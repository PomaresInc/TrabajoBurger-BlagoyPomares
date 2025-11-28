package ies.sequeros.com.dam.pmdm.administrador.modelo
import kotlinx.serialization.Serializable
@Serializable

data class Categoria(
    var id: String,
    var name: String,
    var description: String,
    var imagePath: String,
    var enabled: Boolean
)


