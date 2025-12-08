package ies.sequeros.com.dam.pmdm.administrador.ui.productos.form

data class ProductoFormState (
    val name: String = "",
    val enabled: Boolean = false,
    val imagePath:String="default",
    val price: Double = 0.0,
    val description: String = "",
    val categoria: String = "", // categoriaId?
    val isadmin: Boolean  = false,

    // errores (null = sin error)
    val nombreError: String? = null,
    val imagePathError: String? = null,
    val categoriaError: String? = null,
    val priceError: Double? = null,
    val descriptionError: String? = null,


    // para controlar si se intentó enviar (mostrar errores globales)
    val submitted: Boolean = false

)
