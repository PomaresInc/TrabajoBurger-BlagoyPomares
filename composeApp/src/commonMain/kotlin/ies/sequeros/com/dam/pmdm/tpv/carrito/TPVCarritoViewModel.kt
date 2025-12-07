package ies.sequeros.com.dam.pmdm.tpv.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ies.sequeros.com.dam.pmdm.tpv.carrito.TPVCarritoItem
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID

class TPVCarritoViewModel(
    private val pedidoRepositorio: IPedidoRepositorio? = null,
    private val lineaPedidoRepositorio: ILineaPedidoRepositorio? = null
) : ViewModel() {

    private val _items = MutableStateFlow<List<TPVCarritoItem>>(emptyList())
    val items: StateFlow<List<TPVCarritoItem>> = _items.asStateFlow()

    private val _totalCarrito = MutableStateFlow(0.0)
    val totalCarrito: StateFlow<Double> = _totalCarrito.asStateFlow()

    private val _cantidadTotalProductos = MutableStateFlow(0)
    val cantidadTotalProductos: StateFlow<Int> = _cantidadTotalProductos.asStateFlow()

    private fun recalcularTotales() {
        _totalCarrito.value = _items.value.sumOf { it.subtotal }
        _cantidadTotalProductos.value = _items.value.sumOf { it.cantidad }
    }

    fun agregarProducto(productoId: String, nombre: String, precio: Double, imagePath: String) {
        val itemsActuales = _items.value.toMutableList()
        val itemExistente = itemsActuales.find { it.productoId == productoId }

        if (itemExistente != null) {
            val index = itemsActuales.indexOf(itemExistente)
            itemsActuales[index] = itemExistente.copy(cantidad = itemExistente.cantidad + 1)
        } else {
            itemsActuales.add(
                TPVCarritoItem(
                    productoId = productoId,
                    nombre = nombre,
                    precio = precio,
                    imagePath = imagePath
                )
            )
        }
        _items.value = itemsActuales
        recalcularTotales()
    }

    fun eliminarProducto(productoId: String) {
        _items.value = _items.value.filter { it.productoId != productoId }
        recalcularTotales()
    }

    fun actualizarCantidad(productoId: String, nuevaCantidad: Int) {
        if (nuevaCantidad <= 0) {
            eliminarProducto(productoId)
            return
        }

        val itemsActuales = _items.value.toMutableList()
        val index = itemsActuales.indexOfFirst { it.productoId == productoId }

        if (index != -1) {
            itemsActuales[index] = itemsActuales[index].copy(cantidad = nuevaCantidad)
            _items.value = itemsActuales
            recalcularTotales()
        }
    }

    fun vaciarCarrito() {
        _items.value = emptyList()
        recalcularTotales()
    }

    suspend fun guardarPedido(nombreCliente: String): Boolean {
        if (pedidoRepositorio == null || lineaPedidoRepositorio == null) {
            println("Error: Repositorios no inicializados")
            return false
        }

        if (_items.value.isEmpty()) {
            println("Error: El carrito está vacío")
            return false
        }

        return try {
            // Generar ID único para el pedido
            val pedidoId = UUID.randomUUID().toString()

            // Crear el pedido (la fecha se establece automáticamente en la BD)
            val pedido = Pedido(
                id = pedidoId,
                fecha = null, // MySQL establecerá la fecha con CURRENT_TIMESTAMP
                total = _totalCarrito.value,
                enregado = false,
                client_name = nombreCliente,
                dependienteId = "TPV" // ID por defecto para pedidos desde TPV
            )

            // Guardar el pedido
            pedidoRepositorio.add(pedido)

            // Guardar las líneas de pedido
            _items.value.forEach { item ->
                val lineaPedido = LineaPedido(
                    id = UUID.randomUUID().toString(),
                    unidades = item.cantidad,
                    priceUnit = item.precio,
                    pedidoId = pedidoId,
                    productoId = item.productoId
                )
                lineaPedidoRepositorio.add(lineaPedido)
            }

            // Vaciar el carrito después de guardar
            vaciarCarrito()

            println("Pedido guardado exitosamente: $pedidoId")
            true
        } catch (e: Exception) {
            println("Error al guardar el pedido: ${e.message}")
            e.printStackTrace()
            false
        }
    }
}