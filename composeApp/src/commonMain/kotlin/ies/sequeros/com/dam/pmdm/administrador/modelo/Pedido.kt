package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable

@Serializable

data class Pedido (
    var id:String,
    var fecha: String, // Hay que ver como ponerlo sino para date
    var total: Double,
    var client_name: String,
    var dependienteId: String,
)