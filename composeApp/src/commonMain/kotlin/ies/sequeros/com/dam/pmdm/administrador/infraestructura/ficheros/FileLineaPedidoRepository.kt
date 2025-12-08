package ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros

import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import kotlinx.serialization.json.Json
import java.io.File

class FileLineaPedidoRepository(
    private val almacenDatos: AlmacenDatos,
    private val fileName: String = "linea_pedido.json"
) : ILineaPedidoRepositorio {

    private val subdirectory = "/data/"

    init {
    }

    private suspend fun getDirectoryPath(): String {
        val dir = almacenDatos.getAppDataDir()
        val directory = File(dir, subdirectory)
        return directory.absolutePath
    }

    private suspend fun save(items: List<LineaPedido>) {
        if (!File(this.getDirectoryPath()).exists())
            File(this.getDirectoryPath()).mkdirs()
        this.almacenDatos.writeFile(
            this.getDirectoryPath() + "/" + this.fileName,
            Json.encodeToString(items)
        )
    }


    override suspend fun add(item: LineaPedido) {
        val items = this.getAll().toMutableList()

        if (items.firstOrNull { it.id == item.id } == null) {
            items.add(item)
        } else {
            throw IllegalArgumentException(
                "ALTA: La línea de pedido con id: " + item.id + " ya existe"
            )
        }
        this.save(items)
    }

    override suspend fun remove(item: LineaPedido): Boolean {
        return this.remove(item.id)
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
                "BORRADO: La línea de pedido con id: " + id + " no existe"
            )
        }
    }

    override suspend fun update(item: LineaPedido): Boolean {
            val items = this.getAll().toMutableList()
            val newItems = items.map {
                if (it.id == item.id) item else it
            }.toMutableList()
            this.save(newItems)
            return true
    }


    override suspend fun getAll(): List<LineaPedido> {
        val path = getDirectoryPath() + "/" + this.fileName
        val items = mutableListOf<LineaPedido>()
        var json = ""

        if (File(path).exists()) {
            json = almacenDatos.readFile(path)
            if (json.isNotEmpty())
                items.addAll(Json.decodeFromString<List<LineaPedido>>(json))
        }
        return items.toList()
    }

    override suspend fun findByPedidoId(pedidoId: String): List<LineaPedido> {
        val elements = this.getAll()
        return elements.filter { it.pedidoId == pedidoId }
    }

    override suspend fun findByProductoId(productoId: String): List<LineaPedido> {
        val elements = this.getAll()
        return elements.filter { it.productoId == productoId }
    }

    override suspend fun findByLineaPedidoId(lineaPedidoId: String): LineaPedido {
        val elements = this.getAll()
        return elements.firstOrNull { it.id == lineaPedidoId }
            ?: throw IllegalArgumentException(
                "No se encontró la línea de pedido con id: $lineaPedidoId"
            )
    }

}