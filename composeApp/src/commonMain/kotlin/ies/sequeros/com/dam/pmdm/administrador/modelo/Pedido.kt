package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable

data class Pedido (
    var id:String,
    var fecha: LocalDate,
    var total: Double,
    var client_name: String,
    var dependienteId: String,
)