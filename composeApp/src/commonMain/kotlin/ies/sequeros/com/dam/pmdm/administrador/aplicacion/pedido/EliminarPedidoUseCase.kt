package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido

import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio

class EliminarPedidoUseCase(
    private val pedidoRepo: IPedidoRepositorio,
    private val lineaRepo: ILineaPedidoRepositorio
) {
    suspend fun invoke(id: String) {
        val pedido = pedidoRepo.findById(id) ?: return

        // Obtener todas las líneas del pedido y eliminarlas
        val lineas = lineaRepo.findByPedidoId(id)
        lineas.forEach { linea ->
            lineaRepo.remove(linea)
        }
        
        pedidoRepo.remove(id)

    }
}