package ies.sequeros.com.dam.pmdm.administrador.infraestructura.LineaPedido

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.lineapedido.BBDDRepositorioLineaPedidoJava
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido

class BBDDLienaPedidoRepository(
    private val bbddRepositorioLineaPedidoJava: BBDDRepositorioLineaPedidoJava
): ILineaPedidoRepositorio {
    override suspend fun add(item: LineaPedido) {
        bbddRepositorioLineaPedidoJava.add(item)
    }

    override suspend fun remove(item: LineaPedido): Boolean {
        bbddRepositorioLineaPedidoJava.delete(item)
        return true
    }

    override suspend fun remove(id: String): Boolean {
        bbddRepositorioLineaPedidoJava.remove(id)
        return true
    }

    override suspend fun update(item: LineaPedido): Boolean {
        bbddRepositorioLineaPedidoJava.update(item)
        return true
    }

    override suspend fun getAll(): List<LineaPedido> {
        return bbddRepositorioLineaPedidoJava.getAll()
    }

    override suspend fun findByPedidoId(pedidoId: String): List<LineaPedido> {
        return bbddRepositorioLineaPedidoJava.findByPedidoId(pedidoId)
    }

    override suspend fun findByProductoId(productoId: String): List<LineaPedido> {
        return bbddRepositorioLineaPedidoJava.findByProductoId(productoId)
    }

    override suspend fun findByLineaPedidoId(lineaPedidoId: String): LineaPedido {
        return bbddRepositorioLineaPedidoJava.findByLineaPedidoId(lineaPedidoId)
    }


}