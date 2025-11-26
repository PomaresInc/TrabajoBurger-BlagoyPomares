package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable
@Serializable

data class Producto (
    var id: String,
    var name: String,
    var price: Double,
    var image_path: String,
    var description: String,
    var enabled: Boolean,
    var categoriaId: String
)