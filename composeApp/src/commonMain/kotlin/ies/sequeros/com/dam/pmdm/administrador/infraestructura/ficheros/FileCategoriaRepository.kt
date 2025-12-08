package ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros

import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.serialization.json.Json
import java.io.File

class FileCategoriaRepository(
    private val almacenDatos: AlmacenDatos,
    private val file: String = "categorias.json"
) : ICategoriaRepositorio {

    private val subdirectory = "/data/"

    init {

    }

    private suspend fun getDirectoryPath(): String {
        val dir = almacenDatos.getAppDataDir()
        val directory = File(dir, subdirectory)
        return directory.absolutePath
    }

    private suspend fun save(items: List<Categoria>) {
        if (!File(this.getDirectoryPath()).exists())
            File(this.getDirectoryPath()).mkdirs()
        this.almacenDatos.writeFile(
            this.getDirectoryPath() + "/" + this.file,
            Json.encodeToString(items)
        )
    }

    override suspend fun add(item: Categoria) {
        val items = this.getAll().toMutableList()

        if (items.firstOrNull { it.id == item.id } == null) {
            items.add(item)
        } else {
            throw IllegalArgumentException("ALTA: La categoría con id: " + item.id + " ya existe")
        }
        this.save(items)
    }

    override suspend fun remove(id: String): Boolean {
        val items = this.getAll().toMutableList()
        val item = items.firstOrNull { it.id == id }

        if (item != null) {
            items.remove(item)
            this.save(items)
            return true
        } else {
            throw IllegalArgumentException(
                "BORRADO: La categoría con id: " + id + " no existe"
            )
        }
    }

    override suspend fun findByName(name: String): Categoria? {
        val elements = this.getAll()
        for (element in elements) {
            if (element.name == name)
                return element
        }
        return null
    }

    override suspend fun getById(id: String): Categoria? {
        val elements = this.getAll()
        for (element in elements) {
            if (element.id == id)
                return element
        }
        return null
    }

    override suspend fun getAll(): List<Categoria> {
        val path = getDirectoryPath() + "/" + this.file
        val items = mutableListOf<Categoria>()
        var json = ""

        if (File(path).exists()) {
            json = almacenDatos.readFile(path)
            if (json.isNotEmpty())
                items.addAll(Json.decodeFromString<List<Categoria>>(json))
        }
        return items.toList()
    }

    override suspend fun update(item: Categoria): Boolean {
        val items = this.getAll().toMutableList()
        val newItems = items.map {
            if (it.id == item.id) item else it
        }.toMutableList()
        this.save(newItems)
        return true
    }


}