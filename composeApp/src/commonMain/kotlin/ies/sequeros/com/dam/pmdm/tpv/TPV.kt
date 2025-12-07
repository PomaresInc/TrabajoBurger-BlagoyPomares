package ies.sequeros.com.dam.pmdm.tpv

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.tpv.categorias.TPVCategorias
import ies.sequeros.com.dam.pmdm.tpv.categorias.TPVCategoriasViewModel
import ies.sequeros.com.dam.pmdm.tpv.cliente.TPVCliente
import ies.sequeros.com.dam.pmdm.tpv.cliente.TPVClienteViewModel
import ies.sequeros.com.dam.pmdm.tpv.productos.TPVProductos
import ies.sequeros.com.dam.pmdm.tpv.productos.TPVProductosViewModel
import ies.sequeros.com.dam.pmdm.tpv.carrito.TPVCarritoViewModel
import ies.sequeros.com.dam.pmdm.tpv.carrito.TPVCarrito
@Suppress("ViewModelConstructorInComposable")
@Composable
fun TPV(
    categoriaRepositorio: ICategoriaRepositorio,
    productoRepositorio: IProductoRepositorio,
    almacenDatos: AlmacenDatos,
    pedidoRepositorio: ies.sequeros.com.dam.pmdm.administrador.modelo.IPedidoRepositorio,
    lineaPedidoRepositorio: ies.sequeros.com.dam.pmdm.administrador.modelo.ILineaPedidoRepositorio
) {
    val navController = rememberNavController()
    val tpvStateViewModel = viewModel { TPVStateViewModel() }
    val carritoViewModel = viewModel { 
        TPVCarritoViewModel(pedidoRepositorio, lineaPedidoRepositorio) 
    }
    
    val clientName by tpvStateViewModel.clientName.collectAsState()
    val selectedCategoriaId by tpvStateViewModel.selectedCategoriaId.collectAsState()
    val selectedCategoriaNombre by tpvStateViewModel.selectedCategoriaNombre.collectAsState()
    
    val carritoItems by carritoViewModel.items.collectAsState()
    val totalCarrito by carritoViewModel.totalCarrito.collectAsState()
    val cantidadProductos by carritoViewModel.cantidadTotalProductos.collectAsState()
    
    NavHost(
        navController = navController,
        startDestination = TPVRoutes.CLIENTE
    ) {
        // Pantalla de entrada de nombre del cliente
        composable(TPVRoutes.CLIENTE) {
            val clienteViewModel = remember { TPVClienteViewModel() }
            
            TPVCliente(
                viewModel = clienteViewModel,
                onContinuar = { name ->
                    tpvStateViewModel.setClientName(name)
                    navController.navigate(TPVRoutes.CATEGORIAS)
                }
            )
        }
        
        // Pantalla de selección de categorías
        composable(TPVRoutes.CATEGORIAS) {
            val categoriasViewModel = remember {
                TPVCategoriasViewModel(categoriaRepositorio, almacenDatos)
            }
            
            TPVCategorias(
                clientName = clientName,
                viewModel = categoriasViewModel,
                cantidadProductosCarrito = cantidadProductos,
                totalCarrito = totalCarrito,
                onCategoriaSelected = { categoria ->
                    tpvStateViewModel.setSelectedCategoria(categoria.id, categoria.name)
                    navController.navigate(TPVRoutes.PRODUCTOS)
                },
                onVerCarrito = {
                    navController.navigate(TPVRoutes.CARRITO)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Pantalla de productos de una categoría
        composable(TPVRoutes.PRODUCTOS) {
            val productosViewModel = remember(selectedCategoriaId) {
                TPVProductosViewModel(productoRepositorio, almacenDatos, selectedCategoriaId ?: "")
            }

            TPVProductos(
                categoriaNombre = selectedCategoriaNombre,
                viewModel = productosViewModel,
                cantidadProductosCarrito = cantidadProductos,
                totalCarrito = totalCarrito,
                onProductoSelected = { producto ->
                    carritoViewModel.agregarProducto(
                        productoId = producto.id,
                        nombre = producto.name,
                        precio = producto.price,
                        imagePath = producto.imagePath
                    )
                },
                onVerCarrito = {
                    navController.navigate(TPVRoutes.CARRITO)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Pantalla del carrito
        composable(TPVRoutes.CARRITO) {
            val scope = rememberCoroutineScope()
            
            TPVCarrito(
                clientName = clientName,
                items = carritoItems,
                total = totalCarrito,
                onCantidadChange = { productoId, cantidad ->
                    carritoViewModel.actualizarCantidad(productoId, cantidad)
                },
                onEliminar = { productoId ->
                    carritoViewModel.eliminarProducto(productoId)
                },
                onConfirmarPedido = {
                    scope.launch {
                        val exito = carritoViewModel.guardarPedido(clientName)
                        if (exito) {
                            // Volver a la pantalla principal después de guardar
                            navController.navigate(TPVRoutes.CLIENTE) {
                                popUpTo(TPVRoutes.CLIENTE) { inclusive = true }
                            }
                        }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}