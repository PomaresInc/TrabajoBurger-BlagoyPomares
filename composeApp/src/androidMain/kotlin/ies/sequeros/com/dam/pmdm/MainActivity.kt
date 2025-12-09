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

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros.FileCategoriaRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros.FileDependienteRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros.FileLineaPedidoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros.FilePedidoRepository
import ies.sequeros.com.dam.pmdm.administrador.infraestructura.ficheros.FileProductoRepository
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            // Crear almacén de datos para Android
            val almacenDatos: AlmacenDatos = AlmacenDatos(this)
            // para bcrypt
            val hasher = BcryptPasswordHasher()

            // Usando ficheros para todos los repositorios en móvil
            val dependienteRepositorio: IDependienteRepositorio =
                FileDependienteRepository(almacenDatos, hasher)

            val categoriaRepositorio: ICategoriaRepositorio =
                FileCategoriaRepository(almacenDatos)

            val productoRepositorio: IProductoRepositorio =
                FileProductoRepository(almacenDatos)

            val pedidoRepositorio: IPedidoRepositorio =
                FilePedidoRepository(almacenDatos)

            val lineaPedidoRepositorio: ILineaPedidoRepositorio =
                FileLineaPedidoRepository(almacenDatos)

            App(
                dependienteRepositorio,
                categoriaRepositorio,
                pedidoRepositorio,
                productoRepositorio,
                lineaPedidoRepositorio,
                almacenDatos
            )
        }
    }
}
