package ies.sequeros.com.dam.pmdm.administrador.aplicacion.lineapedido.listar

data class LineaPedidoDTO(
    val unidades: Int,
    val priceUnit: Double,
    val pedidoId: String,
    val productoId: String
)
