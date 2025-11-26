package ies.sequeros.com.dam.pmdm.administrador.modelo

interface ILineaPedidoRepositorio {

    suspend fun add(item:LineaPedido):Unit

    suspend fun remove(item:LineaPedido): Boolean

    suspend fun remove(id:String): Boolean

    suspend fun update(item:LineaPedido): Boolean

    suspend fun getAll():List<LineaPedido>

    suspend fun findByPedidoId(pedidoId:String):List<LineaPedido>

    suspend fun findByProductoId(productoId:String):List<LineaPedido>

    suspend fun findByLineaPedidoId(lineaPedidoId:String):LineaPedido

}