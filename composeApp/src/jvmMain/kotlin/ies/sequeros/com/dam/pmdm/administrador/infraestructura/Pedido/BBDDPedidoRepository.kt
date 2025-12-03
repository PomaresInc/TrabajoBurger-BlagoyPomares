package ies.sequeros.com.dam.pmdm.administrador.infraestructura.Pedido

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedido.BBDDRepositorioPedidoJava
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido

class BBDDPedidoRepository (
    private val bbddRepositorioPedidoJava: BBDDRepositorioPedidoJava): IPedidoRepositorio {

    override suspend fun add(item: Pedido) {
        bbddRepositorioPedidoJava.add(item)
    }

    override suspend fun remove(item: Pedido): Boolean {
        bbddRepositorioPedidoJava.remove(item.id)
        return true
    }

    override suspend fun remove(id: String): Boolean {
        bbddRepositorioPedidoJava.remove(id)
        return true
    }

    override suspend fun modify(item: Pedido): Boolean {
        bbddRepositorioPedidoJava.update(item)
        return true
    }

    override suspend fun getAll(): List<Pedido> {
        return bbddRepositorioPedidoJava.getAll()
    }

    override suspend fun findById(id: String): Pedido? {
        return bbddRepositorioPedidoJava.getById(id)
    }

}