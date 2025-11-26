package ies.sequeros.com.dam.pmdm.administrador.modelo

interface IPedidoRepositorio {

    suspend fun add(item:Pedido):Unit

    suspend fun remove(item:Pedido): Boolean

    suspend fun remove(id:String): Boolean

    suspend fun modify(item:Pedido): Boolean

    suspend fun getAll():List<Pedido>

    suspend fun findById(ids:List<String>):List<Pedido>

}