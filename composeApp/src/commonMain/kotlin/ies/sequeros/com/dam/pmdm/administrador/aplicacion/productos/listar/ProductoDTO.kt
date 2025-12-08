package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar

data class ProductoDTO(
    val name: String,
    val id: String,
    val price: String,
    val imagePath: String,
    val description: String,
    val categoriaId: String,
    val enabled: Boolean,
    val isAdmin: Boolean
)
