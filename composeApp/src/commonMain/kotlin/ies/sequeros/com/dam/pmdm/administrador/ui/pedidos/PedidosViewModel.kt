package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.listar.ListarPedidoUseCase
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.listar.PedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PedidosViewModel(
    private val pedidoRepositorio: IPedidoRepositorio,
    private val lineaPedidoRepositorio: ILineaPedidoRepositorio,
    private val productoRepositorio: IProductoRepositorio,
    val almacenDatos: AlmacenDatos
) : ViewModel() {
    
    private val listarPedidosUseCase: ListarPedidoUseCase

    private val _items = MutableStateFlow<MutableList<PedidoDTO>>(mutableListOf())
    val items: StateFlow<List<PedidoDTO>> = _items.asStateFlow()
    private val _selected = MutableStateFlow<PedidoDTO?>(null)
    val selected = _selected.asStateFlow()

    init {
        listarPedidosUseCase = ListarPedidoUseCase(
            pedidoRepositorio, 
            lineaPedidoRepositorio,
            productoRepositorio,
            almacenDatos
        )
        
        viewModelScope.launch {
            val items = listarPedidosUseCase.invoke()
            _items.value.clear()
            _items.value.addAll(items)
        }
    }

    fun setSelectedPedido(item: PedidoDTO?) {
        _selected.value = item
    }

    fun refresh() {
        viewModelScope.launch {
            val items = listarPedidosUseCase.invoke()
            _items.value.clear()
            _items.value.addAll(items)
        }
    }
}
