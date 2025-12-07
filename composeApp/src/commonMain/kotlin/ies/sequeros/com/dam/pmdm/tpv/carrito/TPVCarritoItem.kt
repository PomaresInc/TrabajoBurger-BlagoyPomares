package ies.sequeros.com.dam.pmdm.tpv.carrito

data class TPVCarritoItem(
    val productoId: String,
    val nombre: String,
    val precio: Double,
    val imagePath: String,
    val cantidad: Int = 1
) {
    val subtotal: Double
        get() = precio * cantidad
}