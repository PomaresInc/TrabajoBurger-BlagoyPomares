package ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.listar

import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ListarPedidoUseCase(
    private val repositorio: IPedidoRepositorio,
    private val lineaRepositorio: ILineaPedidoRepositorio,
    private val productoRepositorio: IProductoRepositorio,
    val almacenDatos: AlmacenDatos
) {
    suspend fun invoke(): List<PedidoDTO> {
        val pedidos = repositorio.getAll()

        return pedidos.map { pedido ->
            val lineas = lineaRepositorio.findByPedidoId(pedido.id)
            val lineasDTO = lineas.mapNotNull { linea ->
                try {
                    val producto = productoRepositorio.getById(linea.productoId)
                    if (producto != null) {
                        LineaPedidoDTO(
                            productoNombre = producto.name,
                            unidades = linea.unidades,
                            // Convertimos el Double a String para el DTO
                            precioUnitario = linea.priceUnit.toString()
                        )
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null
                }
            }

            pedido.toDTO().apply {
                this.lineas = lineasDTO
            }
        }
    }
}