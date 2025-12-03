package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar

class ProductoDTO(
    val name: String,
    val id: String,
    val price: Double,
    val imagePath: String,
    val description: String,
    val categoriaId: String,
    val enabled: Boolean
)
