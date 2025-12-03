package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.modificar

data class ModificarPedidoCommand(
    val pedidoId: String,
    val cliente: String?,
    val dependienteId: String?,
    val lineas: List<LineaPedidoCommand>?
)

data class LineaPedidoCommand(
    val productoId: String,
    val cantidad: Int
)
