package ies.sequeros.com.dam.pmdm.administrador.ui.productos

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.actualizar.ActualizarDependienteCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.cambiarpermisos.CambiarPermisosCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.dependientes.listar.DependienteDTO

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.BorrarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar.ActivarProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar.ActivarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar.ActualizarProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar.ActualizarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear.CrearProductoCommand
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear.CrearProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ListarProductoUseCase
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.FileDependienteRepository
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.administrador.modelo.Dependiente
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministradorViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.dependientes.form.DependienteFormState
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.form.ProductoFormState

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductosViewModel (
    private val productoRepositorio: IProductoRepositorio,
            val almacenDatos: AlmacenDatos
): ViewModel() {
    // los casos de uso se crean dentro para la recomposición
    private val borrarProductoUseCase: BorrarProductoUseCase
    private val crearProductoUseCase: CrearProductoUseCase
    private val listarProductoUseCase: ListarProductoUseCase
    private val actualizarProductoUseCase: ActualizarProductoUseCase
    private val activarProductoUseCase: ActivarProductoUseCase

    private val _items = MutableStateFlow<MutableList<ProductoDTO>>(mutableListOf())
    val items: StateFlow<List<ProductoDTO>> = _items.asStateFlow()
    private val _selected = MutableStateFlow<ProductoDTO?>(null)
    val selected = _selected.asStateFlow()

    init {
        actualizarProductoUseCase = ActualizarProductoUseCase(productoRepositorio,almacenDatos)
        borrarProductoUseCase = BorrarProductoUseCase(productoRepositorio,almacenDatos)
        crearProductoUseCase = CrearProductoUseCase(productoRepositorio,almacenDatos)
        listarProductoUseCase = ListarProductoUseCase(productoRepositorio,almacenDatos)
        activarProductoUseCase = ActivarProductoUseCase(productoRepositorio,almacenDatos)
        viewModelScope.launch {
            var items = listarProductoUseCase.invoke()
            _items.value.clear()
            _items.value.addAll(items)

        }
    }

    fun setSelectedProducto(item: ProductoDTO?){
        _selected.value = item
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
        val command = CrearProductoCommand (
            formState.price,
            formState.name,
            formState.description,
            formState.imagePath,
            formState.categoria,
            formState.enabled,
        )
        viewModelScope.launch {
            try {
                val user = crearProductoUseCase.invoke(command)
                _items.value = (_items.value + user) as MutableList<ProductoDTO>
            }catch (e:Exception){
                throw  e

            }
        }
    }

    fun update(formState: ProductoFormState) {
        val command = ActualizarProductoCommand(
            selected.value!!.id!!,
            formState.name,
            formState.description,
            formState.imagePath,
            formState.enabled
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






























