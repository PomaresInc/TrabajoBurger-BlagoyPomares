package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar

data class ActualizarProductoCommand (
    val id: String,
    val name: String,
    val description: String,
    val imagePath: String,
    val enabled: Boolean,
)