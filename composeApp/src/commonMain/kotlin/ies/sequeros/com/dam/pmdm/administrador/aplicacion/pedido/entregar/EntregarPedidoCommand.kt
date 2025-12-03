package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.entregar

data class EntregarPedidoCommand(
    val pedidoId: String,
    val lineasIds: List<String>)
