package ies.sequeros.com.dam.pmdm.administrador.ui.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.ListarCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.BorrarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar.ActivarProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar.ActivarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar.ActualizarProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar.ActualizarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear.CrearProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear.CrearProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ListarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.form.ProductoFormState
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductosViewModel (
    private val productoRepositorio: IProductoRepositorio,
    private val categoriaRepositorio: ICategoriaRepositorio, // Para listar categorías
    val almacenDatos: AlmacenDatos
): ViewModel() {
    // los casos de uso se crean dentro para la recomposición
    private val borrarProductoUseCase: BorrarProductoUseCase
    private val crearProductoUseCase: CrearProductoUseCase
    private val listarProductoUseCase: ListarProductoUseCase
    private val actualizarProductoUseCase: ActualizarProductoUseCase
    private val activarProductoUseCase: ActivarProductoUseCase
    private val listarCategoriasUseCase: ListarCategoriaUseCase // UseCase para categorías

    private val _items = MutableStateFlow<MutableList<ProductoDTO>>(mutableListOf())
    val items: StateFlow<List<ProductoDTO>> = _items.asStateFlow()
    private val _selected = MutableStateFlow<ProductoDTO?>(null)
    val selected = _selected.asStateFlow()

    // AÑADIDO: Estado para la lista del ComboBox
    private val _categorias = MutableStateFlow<List<CategoriaDTO>>(emptyList())
    val categorias: StateFlow<List<CategoriaDTO>> = _categorias.asStateFlow()

    init {
        actualizarProductoUseCase = ActualizarProductoUseCase(productoRepositorio,almacenDatos)
        borrarProductoUseCase = BorrarProductoUseCase(productoRepositorio,almacenDatos)
        crearProductoUseCase = CrearProductoUseCase(productoRepositorio,almacenDatos)
        listarProductoUseCase = ListarProductoUseCase(productoRepositorio,almacenDatos)
        activarProductoUseCase = ActivarProductoUseCase(productoRepositorio,almacenDatos)

        listarCategoriasUseCase = ListarCategoriaUseCase(categoriaRepositorio,almacenDatos)

        viewModelScope.launch {
            // Cargar productos
            var items = listarProductoUseCase.invoke()
            _items.value.clear()
            _items.value.addAll(items)

            // Cargar categorías para el ComboBox
            val listaCategorias = listarCategoriasUseCase.invoke()
            _categorias.value = listaCategorias
        }
    }

    fun setSelectedProducto(item: ProductoDTO?){
        _selected.value = item
    }

    fun onPriceChange(newPrice: String) {
        _selected.value = _selected.value?.copy(price = newPrice)
    }

    fun switchEnableProducto(item: ProductoDTO) {
        val command= ActivarProductoCommand(
            item.id,
            item.enabled,
        )

        viewModelScope.launch {
            val item=activarProductoUseCase.invoke(command)

            _items.value = _items.value.map {
                if (item.id == it.id)
                    item
                else
                    it
            } as MutableList<ProductoDTO>
        }

    }

    // switchAdmin NO
    fun delete(item: ProductoDTO) {
        viewModelScope.launch {
            borrarProductoUseCase.invoke(item.id)
            _items.update { current ->
                current.filterNot { it.id == item.id }.toMutableList()
            }

        }
    }



    fun add(formState: ProductoFormState) {

        val command = CrearProductoCommand(
            price = formState.price,
            name = formState.name,
            description = formState.description,
            imagePath = formState.imagePath,
            categoriaId = formState.categoria,
            enabled = formState.enabled,
        )
        viewModelScope.launch {
            try {
                val producto = crearProductoUseCase.invoke(command)
                _items.value = (_items.value + producto) as MutableList<ProductoDTO>
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun update(formState: ProductoFormState) {

        val command = ActualizarProductoCommand(
            id = selected.value!!.id!!,
            price = formState.price,
            name = formState.name,
            description = formState.description,
            imagePath = formState.imagePath,
            enabled = formState.enabled,
            categoria = formState.categoria
        )
        viewModelScope.launch {
            val item = actualizarProductoUseCase.invoke(command)
            _items.update { current ->
                current.map { if (it.id == item.id) item else it } as MutableList<ProductoDTO>
            }
        }
    }

    fun save(item: ProductoFormState) {
        if (_selected.value == null)
            this.add(item)
        else
            this.update(item)
    }

}