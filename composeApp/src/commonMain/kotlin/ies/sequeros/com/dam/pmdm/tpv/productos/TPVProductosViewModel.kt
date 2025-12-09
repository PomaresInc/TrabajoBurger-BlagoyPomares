package ies.sequeros.com.dam.pmdm.tpv.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TPVProductosViewModel(
    private val productoRepositorio: IProductoRepositorio,
    private val almacenDatos: AlmacenDatos,
    private val categoriaId: String
) : ViewModel() {

    private val _productos = MutableStateFlow<List<ProductoDTO>>(emptyList())
    val productos: StateFlow<List<ProductoDTO>> = _productos.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val todosProductos = productoRepositorio.getByCategoria(categoriaId)
                // Filtrar solo los productos habilitados y convertir a DTO
                _productos.value = todosProductos
                    .filter { it.enabled }
                    .map { 
                        it.toDTO(almacenDatos.getAppDataDir() + "/productos/")
                    }
            } catch (e: Exception) {
                _productos.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        cargarProductos()
    }
}
