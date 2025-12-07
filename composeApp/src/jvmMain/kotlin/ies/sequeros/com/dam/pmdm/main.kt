package ies.sequeros.com.dam.pmdm

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.Categoria.BBDDCategoriaRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias.BBDDRepositorioCategoriaJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.Pedido.BBDDPedidoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedido.BBDDRepositorioPedidoJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.Producto.BBDDProductoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.producto.BBDDRepositorioProductoJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.LineaPedido.BBDDLienaPedidoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.lineapedido.BBDDRepositorioLineaPedidoJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.MemDependienteRepository
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import java.io.FileInputStream
import java.util.logging.LogManager

fun main() = application {
    // Usando base de datos para dependientes
    val dependienteRepositorioJava = ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes.BBDDRepositorioDependientesJava("/app.properties")
    val dependienteRepositorio: IDependienteRepositorio = ies.sequeros.com.dam.pmdm.administrador.infraestructura.Dependiente.BBDDDependienteRepository(dependienteRepositorioJava)

    // Usando base de datos para categorías
    val categoriaRepositorioJava = BBDDRepositorioCategoriaJava("/app.properties")
    val categoriaRepositorio: ICategoriaRepositorio = BBDDCategoriaRepository(categoriaRepositorioJava)

    // Usando base de datos para pedidos
    val pedidoRepositorioJava = BBDDRepositorioPedidoJava("/app.properties")
    val pedidoRepositorio: IPedidoRepositorio = BBDDPedidoRepository(pedidoRepositorioJava)

    // Usando base de datos para productos
    val productoRepositorioJava = BBDDRepositorioProductoJava("/app.properties")
    val productoRepositorio: IProductoRepositorio = BBDDProductoRepository(productoRepositorioJava)

    // Usando base de datos para líneas de pedido
    val lineaPedidoRepositorioJava = BBDDRepositorioLineaPedidoJava("/app.properties")
    val lineaPedidoRepositorio: ILineaPedidoRepositorio = BBDDLienaPedidoRepository(lineaPedidoRepositorioJava)

    configureExternalLogging("./logging.properties")
    Window(
        onCloseRequest = {
            // Cerrar la conexión a la base de datos
            categoriaRepositorioJava.close()
            pedidoRepositorioJava.close()
            productoRepositorioJava.close()
            exitApplication()
        },
        title = "VegaBurguer",
    ) {
        //se envuelve el repositorio en java en uno que exista en Kotlin
        App(dependienteRepositorio, categoriaRepositorio, pedidoRepositorio, productoRepositorio, lineaPedidoRepositorio, AlmacenDatos())
    }
}
fun configureExternalLogging(path: String) {
    try {
        FileInputStream(path).use { fis ->
            LogManager.getLogManager().readConfiguration(fis)
            println("Logging configurado desde: $path")
        }
    } catch (e: Exception) {
        println("⚠️ No se pudo cargar logging.properties externo: $path")
        e.printStackTrace()
    }
}