package ies.sequeros.com.dam.pmdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
//import ies.sequeros.com.dam.pmdm.administrador.infraestructura.memoria.FileDependienteRepository
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.commons.infraestructura.util.BcryptPasswordHasher

// TODO para mañana (dia de la entrega) ver como importar correctamente los repository por la mañana a ser posible,
//  he conseguido hacerlo para FileDependienteRepository, hay que ver si hace falta para los demas... Sino tendremos que hacerlos

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros.FileDependienteRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros.FilePedidoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros.FileProductoRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Usando base de datos para dependientes
        val dependienteRepositorioJava = ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes.BBDDRepositorioDependientesJava("/app.properties")
        val dependienteRepositorio: IDependienteRepositorio = ies.sequeros.com.dam.pmdm.administrador.infraestructura.Dependiente.BBDDDependienteRepository(dependienteRepositorioJava)

        // para bc
        val almacenDatos = AlmacenDatos(context = this)
        val hasher = BcryptPasswordHasher()

        val dependienteRepo = FileDependienteRepository(almacenDatos, hasher)

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            //se crean almacenes de datos y de imagenes propias de la plataforma y se
            //pasan a la aplicación,
            val almacenImagenes:AlmacenDatos=  AlmacenDatos(this)

            App(dependienteRepositorio,almacenImagenes)
        }
    }
}
