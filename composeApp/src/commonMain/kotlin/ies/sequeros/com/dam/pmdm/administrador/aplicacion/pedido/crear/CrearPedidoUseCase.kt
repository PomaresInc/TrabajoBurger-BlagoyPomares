package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.crear

import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import java.util.UUID

class CrearPedidoUseCase(
    private val pedidoRepo: IPedidoRepositorio,
    private val lineaRepo: ILineaPedidoRepositorio,
    private val productoRepo: IProductoRepositorio
) {
    suspend fun invoke(command: CrearPedidoCommand) {
        // Crear el pedido (la fecha se establece automáticamente en la BD)
        val pedido = Pedido(
            id = UUID.randomUUID().toString(),
            fecha = null, // MySQL establecerá la fecha con CURRENT_TIMESTAMP
            total = 0.0,
            enregado = false,
            client_name = command.cliente,
            dependienteId = command.dependienteId
        )

        var total = 0.0
        val lineas = command.lineas.map { lineaCmd ->
            // Obtener el precio real del producto desde el repositorio
            val producto = productoRepo.getById(lineaCmd.productoId)
            val priceUnit = producto.price
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
