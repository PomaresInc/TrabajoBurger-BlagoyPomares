package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.listar

import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido

fun Pedido.toDTO(path: String = "") = PedidoDTO(
    id = id,
    fecha = fecha.orEmpty(),
    total = total,
    enregado = enregado,
    client_name = client_name,
    dependienteId = dependienteId
)


