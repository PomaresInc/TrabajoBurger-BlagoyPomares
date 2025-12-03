package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.listar

import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ListarPedidoUseCase(
    private val repositorio: IPedidoRepositorio,
    private val almacenDatos: AlmacenDatos
) {
    suspend fun invoke(): List<PedidoDTO> {
        val items = repositorio.getAll().map { it.toDTO() }
        return items

    }
}