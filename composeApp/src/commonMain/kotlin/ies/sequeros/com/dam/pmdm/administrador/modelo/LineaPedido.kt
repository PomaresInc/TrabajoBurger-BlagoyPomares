package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable
@Serializable

data class LineaPedido (
    var unidades:Int,
    var price_unit:Double,
    var pedidoId:String,
    var productoId:String
)