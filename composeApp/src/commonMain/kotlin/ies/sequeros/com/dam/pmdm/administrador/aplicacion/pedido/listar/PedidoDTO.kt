package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.listar

class PedidoDTO (
    var id: String,
    var fecha: String,
    var total: Double,
    var enregado: Boolean,
    var client_name: String,
    var dependienteId: String,
    var lineas: List<LineaPedidoDTO> = emptyList()
)