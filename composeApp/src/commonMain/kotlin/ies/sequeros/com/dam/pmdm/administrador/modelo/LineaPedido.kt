package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable

data class LineaPedido (
    var id: String,
    var unidades: Int,
    var priceUnit: Double,
    var pedidoId: String,
    var productoId: String
)