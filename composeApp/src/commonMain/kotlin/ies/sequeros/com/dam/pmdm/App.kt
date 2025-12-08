package ies.sequeros.com.dam.pmdm

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.com.dam.pmdm.administrador.AdministradorViewModel
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
import ies.sequeros.com.dam.pmdm.tpv.TPV
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministrador
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministradorViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.dependientes.DependientesViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.categorias.CategoriasViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.pedidos.PedidosViewModel
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductosViewModel

@Suppress("ViewModelConstructorInComposable")
@Composable

fun App( 
    dependienteRepositorio : IDependienteRepositorio,
    categoriaRepositorio: ICategoriaRepositorio,
    pedidoRepositorio: IPedidoRepositorio,
    productoRepositorio: IProductoRepositorio,
    lineaPedidoRepositorio: ILineaPedidoRepositorio,
    almacenImagenes:AlmacenDatos
) {

    //view model
    val appViewModel= viewModel {  AppViewModel() }
    val mainViewModel= remember { MainAdministradorViewModel() }
    val administradorViewModel= viewModel { AdministradorViewModel() }
    val dependientesViewModel = viewModel{ DependientesViewModel(
        dependienteRepositorio, almacenImagenes
    )}
    val categoriasViewModel = viewModel { CategoriasViewModel(
        categoriaRepositorio, almacenImagenes
    )}
    val pedidosViewModel = viewModel { PedidosViewModel(
        pedidoRepositorio, lineaPedidoRepositorio, productoRepositorio, almacenImagenes
    )}
    val productosViewModel = viewModel { ProductosViewModel(
        productoRepositorio, almacenImagenes
    ) }

    appViewModel.setWindowsAdatativeInfo( currentWindowAdaptiveInfo())
    val navController= rememberNavController()
//para cambiar el modo
    AppTheme(appViewModel.darkMode.collectAsState()) {

        NavHost(
            navController,
            startDestination = AppRoutes.Main
        ) {
            composable(AppRoutes.Main) {
                Principal({
                    navController.navigate(AppRoutes.Administrador)
                },{

                },{
                    navController.navigate(AppRoutes.TPV)
                })
            }
            composable (AppRoutes.Administrador){
                MainAdministrador(appViewModel,mainViewModel,administradorViewModel,
                    dependientesViewModel,categoriasViewModel,pedidosViewModel, productosViewModel ,{
                    navController.popBackStack()
                })
            }
            composable(AppRoutes.TPV) {
                TPV(
                    categoriaRepositorio = categoriaRepositorio,
                    productoRepositorio = productoRepositorio,
                    almacenDatos = almacenImagenes,
                    pedidoRepositorio = pedidoRepositorio,
                    lineaPedidoRepositorio = lineaPedidoRepositorio
                )
            }

        }
    }

}