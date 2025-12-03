package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear

class CrearProductoCommand (
    val id: String,
    val price: Double,
    val name: String,
    val description: String,
    val imagePath: String,
    val categoriaId: String,
    val enabled: Boolean,
)