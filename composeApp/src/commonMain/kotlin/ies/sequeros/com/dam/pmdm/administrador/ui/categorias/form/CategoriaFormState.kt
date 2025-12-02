package ies.sequeros.com.dam.pmdm.administrador.ui.categorias.form

data class CategoriaFormState(
    val nombre: String = "",
    val descripcion: String = "",
    val imagePath: String = "",
    val enabled: Boolean = true,
    
    // Errores de validación
    val nombreError: String? = null,
    val descripcionError: String? = null,
    val imagePathError: String? = null,
    val submitted: Boolean = false
)
