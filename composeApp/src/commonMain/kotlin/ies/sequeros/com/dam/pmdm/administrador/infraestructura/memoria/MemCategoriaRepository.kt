package ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria

import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio

class MemCategoriaRepository : ICategoriaRepositorio {

    private val items = hashMapOf<String, Categoria>()
    
    init {
        val c1 = Categoria(
            "cat-001",
            "Hamburguesas",
            "Deliciosas hamburguesas veganas con ingredientes frescos",
            "",
            true
        )
        items[c1.id] = c1
        
        val c2 = Categoria(
            "cat-002",
            "Bebidas",
            "Refrescantes bebidas naturales y saludables",
            "",
            true
        )
        items[c2.id] = c2
        
        val c3 = Categoria(
            "cat-003",
            "Postres",
            "Postres veganos dulces y sabrosos",
            "",
            true
        )
        items[c3.id] = c3
        
        val c4 = Categoria(
            "cat-004",
            "Entrantes",
            "Aperitivos y entradas para comenzar tu comida",
            "",
            false
        )
        items[c4.id] = c4
    }

    override suspend fun add(item: Categoria) {
        if (!items.containsKey(item.id)) {
            items[item.id] = item
        } else {
            throw IllegalArgumentException("ALTA: La categoría con id: ${item.id} ya existe")
        }
    }

    override suspend fun remove(id: String): Boolean {
        if (items.containsKey(id)) {
            items.remove(id)
            return true
        } else {
            throw IllegalArgumentException("BORRADO: La categoría con id: $id NO existe")
        }
    }

    override suspend fun update(item: Categoria): Boolean {
        if (items.containsKey(item.id)) {
            items[item.id] = item
            return true
        } else {
            throw IllegalArgumentException("ACTUALIZACION: La categoría con id: ${item.id} NO existe")
        }
    }

    override suspend fun getAll(): List<Categoria> {
        return items.values.toList()
    }

    override suspend fun getById(id: String): Categoria? {
        return items[id]
    }

    override suspend fun findByName(name: String): Categoria? {
        return items.values.find { it.name.equals(name, ignoreCase = true) }
    }
}
