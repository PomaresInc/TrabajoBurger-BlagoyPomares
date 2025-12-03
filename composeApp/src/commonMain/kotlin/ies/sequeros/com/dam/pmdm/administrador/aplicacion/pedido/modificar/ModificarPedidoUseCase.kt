package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.modificar

import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido

class ModificarPedidoUseCase(
    private val pedidoRepo: IPedidoRepositorio,
    private val lineaRepo: ILineaPedidoRepositorio
) {
    suspend fun invoke(command: ModificarPedidoCommand) {
        val pedido = pedidoRepo.findById(command.pedidoId) ?: return
        command.cliente?.let { pedido.client_name = it }
        command.dependienteId?.let { pedido.dependienteId = it }
        pedidoRepo.modify(pedido)
    }
}
