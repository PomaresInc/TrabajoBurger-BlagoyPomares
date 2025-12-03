package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.crear

data class CrearPedidoCommand(
    val cliente: String,
    val dependienteId: String,
    val lineas: List<LineaPedidoCommand>
)

data class LineaPedidoCommand(
    val productoId: String,
    val cantidad: Int
)
