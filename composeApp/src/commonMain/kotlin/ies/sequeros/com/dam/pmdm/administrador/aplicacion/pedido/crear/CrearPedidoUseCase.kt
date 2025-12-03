package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.crear

import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import kotlinx.datetime.Clock
import java.util.UUID

class CrearPedidoUseCase(
    private val pedidoRepo: IPedidoRepositorio,
    private val lineaRepo: ILineaPedidoRepositorio
) {
    suspend fun invoke(command: CrearPedidoCommand) {
        // Crear el pedido
        val pedido = Pedido(
            id = UUID.randomUUID().toString(),
            fecha = Clock.System.now().toString(),
            total = 0.0,
            enregado = false,
            client_name = command.cliente,
            dependienteId = command.dependienteId
        )

        var total = 0.0
        val lineas = command.lineas.map { lineaCmd ->
            val priceUnit = 0.0
            total += priceUnit * lineaCmd.cantidad
            LineaPedido(
                id = UUID.randomUUID().toString(),
                unidades = lineaCmd.cantidad,
                priceUnit = priceUnit,
                pedidoId = pedido.id,
                productoId = lineaCmd.productoId
            )
        }
        pedido.total = total


        pedidoRepo.add(pedido)
        lineas.forEach { lineaRepo.add(it) }
    }
}
