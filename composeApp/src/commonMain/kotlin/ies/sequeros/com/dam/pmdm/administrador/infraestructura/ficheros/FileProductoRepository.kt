package ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros

import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import java.io.File
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FileProductoRepository(
    private val almacenDatos: AlmacenDatos,
    private val fileName: String = "productos.json"
) : IProductoRepositorio {

    private val subdirectory = "/data/"

    init {

    }

    private suspend fun getDirectoryPath(): String {
        val dir = almacenDatos.getAppDataDir()
        val directory = File(dir, subdirectory)

        return directory.absolutePath
    }

    private suspend fun save(items: List<Producto>) {
        if (!File(this.getDirectoryPath()).exists())
            File(this.getDirectoryPath()).mkdirs()
        this.almacenDatos.writeFile(this.getDirectoryPath() + "/" + this.fileName, Json.encodeToString(items))
    }

    override suspend fun add(item: Producto) {
        val items = this.getAll().toMutableList()

        // Comprobamos por ID para evitar que puedan estar duplicados
        if (items.firstOrNull { it.id == item.id } == null) {
            items.add(item)
        } else {
            throw IllegalArgumentException("ALTA: El producto con id:" + item.id + " ya existe")
        }
        this.save(items)
    }

    override suspend fun remove(item: Producto): Boolean {
        return this.remove(item.id)
    }

    override suspend fun remove(id: String): Boolean {
        val items = this.getAll().toMutableList()
        var item = items.firstOrNull { it.id == id }
        if (item != null) {
            items.remove((item))
            this.save(items)
            return true
        } else {
            throw IllegalArgumentException(
                "BORRADO:" +
                        " El producto con id:" + id + " NO existe"
            )
        }
        return true
    }

    override suspend fun update(item: Producto): Boolean {
        val items = this.getAll().toMutableList()
        // reemplazamos por si coincide el id
        val newItems = items.map { if (it.id == item.id) item else it }.toMutableList()
        this.save(newItems)
        return true
    }

    override suspend fun getAll(): List<Producto> {
        val path = getDirectoryPath() + "/" + this.fileName
        val items = mutableListOf<Producto>()
        var json = ""
        if (File(path).exists()) {
            json = almacenDatos.readFile(path)
            if (!json.isEmpty())
                items.addAll(Json.decodeFromString<List<Producto>>(json))
        }
        return items.toList()
    }

    override suspend fun getByCategoria(categoriaId: String): List<Producto> {
        val elements = this.getAll()
        val result = mutableListOf<Producto>()
        for (element in elements) {
            if (element.categoriaId == categoriaId) {
                result.add(element)
            }
        }
        return result
    }

    override suspend fun findByName(name: String): Producto? {
        val elements = this.getAll()
        for (element in elements) {
            if (element.name == name)
                return element
        }
        return null
    }

    override suspend fun findByProductoId(productoId: String): Producto {
        return getById(productoId)
    }

    override suspend fun getById(id: String): Producto {
        val elements = this.getAll()
        for (element in elements) {
            if (element.id == id)
                return element
        }
        // lanzamos excepcion por si es null
        throw Exception("Producto no encontrado con id: $id")
    }
}