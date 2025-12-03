package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.consultar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.listar.PedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio

class ConsultarPedidoUseCase(private val pedidoRepo: IPedidoRepositorio) {
    suspend fun invoke(command: ConsultarPedidoCommand): PedidoDTO? {
        return pedidoRepo.findById(command.pedidoId)?.toDTO()
    }
}
