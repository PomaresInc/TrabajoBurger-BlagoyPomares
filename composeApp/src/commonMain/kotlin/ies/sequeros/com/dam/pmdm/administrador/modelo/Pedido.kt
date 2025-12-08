package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable

@Serializable

data class Pedido (
    var id: String,
    var fecha: String? = null,
    var total: Double,
    var enregado: Boolean,
    var client_name: String,
    var dependienteId: String
)