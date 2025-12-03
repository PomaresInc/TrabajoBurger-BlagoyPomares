package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido

import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio

class EliminarPedidoUseCase(
    private val pedidoRepo: IPedidoRepositorio,
    private val lineaRepo: ILineaPedidoRepositorio
) {
    suspend fun invoke(id: String) {
        val pedido = pedidoRepo.findById(id) ?: return

        lineaRepo.remove(id)
        pedidoRepo.remove(id)

    }
}