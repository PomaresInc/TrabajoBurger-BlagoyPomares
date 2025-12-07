package ies.sequeros.com.dam.pmdm.tpv.cliente

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TPVClienteViewModel : ViewModel() {

    private val _clientName = MutableStateFlow("")
    val clientName: StateFlow<String> = _clientName.asStateFlow()

    private val _clientNameError = MutableStateFlow<String?>(null)
    val clientNameError: StateFlow<String?> = _clientNameError.asStateFlow()

    fun onClientNameChange(name: String) {
        _clientName.value = name
        _clientNameError.value = validateClientName(name)
    }

    private fun validateClientName(name: String): String? {
        if (name.isBlank()) return "El nombre del cliente es obligatorio"
        if (name.length < 2) return "El nombre debe tener al menos 2 caracteres"
        return null
    }

    fun isValid(): Boolean {
        val error = validateClientName(_clientName.value)
        _clientNameError.value = error
        return error == null
    }

    fun clear() {
        _clientName.value = ""
        _clientNameError.value = null
    }
}
