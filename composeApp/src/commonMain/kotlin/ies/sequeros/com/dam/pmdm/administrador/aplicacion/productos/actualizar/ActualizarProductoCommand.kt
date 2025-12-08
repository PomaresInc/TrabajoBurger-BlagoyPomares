package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar

data class ActualizarProductoCommand (
    val id: String,
    val price: String,
    val name: String,
    val description: String,
    val imagePath: String,
    val enabled: Boolean,
    val categoria: String
)