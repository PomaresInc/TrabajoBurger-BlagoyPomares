package ies.sequeros.com.dam.pmdm.tpv

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TPVStateViewModel : ViewModel() {
    private val _clientName = MutableStateFlow("")
    val clientName: StateFlow<String> = _clientName.asStateFlow()

    private val _selectedCategoriaId = MutableStateFlow<String?>(null)
    val selectedCategoriaId: StateFlow<String?> = _selectedCategoriaId.asStateFlow()

    private val _selectedCategoriaNombre = MutableStateFlow("")
    val selectedCategoriaNombre: StateFlow<String> = _selectedCategoriaNombre.asStateFlow()

    fun setClientName(name: String) {
        _clientName.value = name
    }

    fun setSelectedCategoria(id: String, nombre: String) {
        _selectedCategoriaId.value = id
        _selectedCategoriaNombre.value = nombre
    }

    fun clearCategoria() {
        _selectedCategoriaId.value = null
        _selectedCategoriaNombre.value = ""
    }
}