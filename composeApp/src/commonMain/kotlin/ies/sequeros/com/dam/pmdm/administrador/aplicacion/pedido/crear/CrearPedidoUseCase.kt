package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.crear

import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import java.math.BigDecimal
import java.util.UUID

class CrearPedidoUseCase(
    private val pedidoRepo: IPedidoRepositorio,
    private val lineaRepo: ILineaPedidoRepositorio,
    private val productoRepo: IProductoRepositorio
) {
    suspend fun invoke(command: CrearPedidoCommand) {
        // Inicializamos el total con BigDecimal para precisión matemática
        var totalBD = BigDecimal.ZERO

        // Preparamos las líneas
        val lineasParaGuardar = command.lineas.map { lineaCmd ->
            // Obtener el precio real del producto
            val producto = productoRepo.getById(lineaCmd.productoId)
            // Manejo de error null
                ?: throw IllegalArgumentException("Producto con ID '${lineaCmd.productoId}' no encontrado")

            // 1. Cálculos precisos en BigDecimal
            val priceUnitBD = producto.price.toBigDecimal()
            val cantidadBD = lineaCmd.cantidad.toBigDecimal()

            // Acumulamos al total
            totalBD += priceUnitBD * cantidadBD

            // 2. Creamos el objeto LineaPedido convirtiendo el precio a Double para guardarlo
            LineaPedido(
                id = UUID.randomUUID().toString(),
                unidades = lineaCmd.cantidad,
                priceUnit = priceUnitBD.toDouble(), // Convertimos a Double aquí
                pedidoId = "", // Lo asignaremos después de generar el ID del pedido
                productoId = lineaCmd.productoId
            )
        }

        // Crear el pedido
        val idPedido = UUID.randomUUID().toString()
        val pedido = Pedido(
            id = idPedido,
            fecha = null,
            total = totalBD.toDouble(),
            enregado = false,
            client_name = command.cliente,
            dependienteId = command.dependienteId
        )

        // Guardamos el pedido
        pedidoRepo.add(pedido)

        // Asignamos el ID del pedido a las líneas y las guardamos
        lineasParaGuardar.forEach { linea ->
            // Creamos una copia con el ID correcto o modificamos si es var (aquí asumo val y data class copy)
            val lineaFinal = linea.copy(pedidoId = idPedido)
            lineaRepo.add(lineaFinal)
        }
    }
}
