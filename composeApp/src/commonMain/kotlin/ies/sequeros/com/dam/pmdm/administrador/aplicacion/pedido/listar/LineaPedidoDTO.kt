package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.listar

data class LineaPedidoDTO(
    val productoNombre: String,
    val unidades: Int,
    val precioUnitario: Double
)
