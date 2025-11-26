package ies.sequeros.com.dam.pmdm.administrador.modelo

interface ICategoriaRepositorio {
    suspend fun add(item:Categoria):Unit
    suspend fun remove(item:Categoria): Boolean
    suspend fun findByName(name:String): Categoria?
    suspend fun getById(id:String):Categoria?
    suspend fun getAll():List<Categoria>
    suspend fun update(item:Categoria): Boolean
}