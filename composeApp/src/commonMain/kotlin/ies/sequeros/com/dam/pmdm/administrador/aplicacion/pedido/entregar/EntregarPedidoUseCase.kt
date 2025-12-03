package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.entregar

import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio

class EntregarPedidoUseCase(private val lineaRepo: ILineaPedidoRepositorio) {
    suspend fun invoke(command: EntregarPedidoCommand, pedidoRepo: IPedidoRepositorio) {
        val pedido = pedidoRepo.findById(command.pedidoId) ?: return
        pedido.enregado = true
        pedidoRepo.modify(pedido)
    }
}
