package ies.sequeros.com.dam.pmdm.tpv.cliente

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TPVClienteViewModel : ViewModel() {

    // Nombre del cliente
    private val _clientName = MutableStateFlow("")
    val clientName: StateFlow<String> = _clientName.asStateFlow()

    private val _clientNameError = MutableStateFlow<String?>(null)
    val clientNameError: StateFlow<String?> = _clientNameError.asStateFlow()

    // Información del restaurante
    private val _restaurantAddress = MutableStateFlow("Calle Principal, 123 - Madrid")
    val restaurantAddress: StateFlow<String> = _restaurantAddress.asStateFlow()

    private val _restaurantLogo = MutableStateFlow("vegaburguer_logo.png")
    val restaurantLogo: StateFlow<String> = _restaurantLogo.asStateFlow()

    // Estado del pedido en curso
    private val _pedidoIniciado = MutableStateFlow(false)
    val pedidoIniciado: StateFlow<Boolean> = _pedidoIniciado.asStateFlow()

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

    fun iniciarPedido() {
        if (isValid()) {
            _pedidoIniciado.value = true
        }
    }

    fun cancelarPedido() {
        _pedidoIniciado.value = false
        clear()
    }

    fun clear() {
        _clientName.value = ""
        _clientNameError.value = null
        _pedidoIniciado.value = false
    }

    fun setRestaurantAddress(address: String) {
        _restaurantAddress.value = address
    }

    fun setRestaurantLogo(logo: String) {
        _restaurantLogo.value = logo
    }
}
