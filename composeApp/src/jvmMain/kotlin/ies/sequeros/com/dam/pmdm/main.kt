package ies.sequeros.com.dam.pmdm

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.Categoria.BBDDCategoriaRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias.BBDDRepositorioCategoriaJava
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.MemDependienteRepository
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import java.io.FileInputStream
import java.util.logging.LogManager

fun main() = application {
    // Usando repositorio en memoria para dependientes (sin base de datos)
    val dependienteRepositorio: IDependienteRepositorio = MemDependienteRepository()
    
    // Usando base de datos para categorías
    val categoriaRepositorioJava = BBDDRepositorioCategoriaJava("/app.properties")
    val categoriaRepositorio: ICategoriaRepositorio = BBDDCategoriaRepository(categoriaRepositorioJava)
    
    // Comentado: Configuración con base de datos para dependientes
    // val dependienteRepositorioJava=BBDDRepositorioDependientesJava("/app.properties")
    // val dependienteRepositorio: IDependienteRepositorio = BBDDDependienteRepository(dependienteRepositorioJava )
    // val procuctoRepositorioJava=BBDDRepositorioDependientesJava("/app.properties")
    // val productoRepositorio: IDependienteRepositorio = BBDDDependienteRepository(procuctoRepositorioJava )

    configureExternalLogging("./logging.properties")
    Window(
        onCloseRequest = {
            // Cerrar la conexión a la base de datos
            categoriaRepositorioJava.close()
            exitApplication()
        },
        title = "VegaBurguer",
    ) {
        //se envuelve el repositorio en java en uno que exista en Kotlin
        App(dependienteRepositorio, categoriaRepositorio, AlmacenDatos())
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