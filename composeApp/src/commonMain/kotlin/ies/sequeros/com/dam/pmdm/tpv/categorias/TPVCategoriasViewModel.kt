package ies.sequeros.com.dam.pmdm.tpv.categorias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.ListarCategoriaUseCase
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TPVCategoriasViewModel(
    private val categoriaRepositorio: ICategoriaRepositorio,
    private val almacenDatos: AlmacenDatos
) : ViewModel() {

    private val listarCategoriasUseCase: ListarCategoriaUseCase =
        ListarCategoriaUseCase(categoriaRepositorio, almacenDatos)

    private val _categorias = MutableStateFlow<List<CategoriaDTO>>(emptyList())
    val categorias: StateFlow<List<CategoriaDTO>> = _categorias.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarCategorias()
    }

    private fun cargarCategorias() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val todasCategorias = listarCategoriasUseCase.invoke()
                // Filtrar solo las categorías habilitadas
                _categorias.value = todasCategorias.filter { it.enabled }
            } catch (e: Exception) {
                _categorias.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        cargarCategorias()
    }
}
