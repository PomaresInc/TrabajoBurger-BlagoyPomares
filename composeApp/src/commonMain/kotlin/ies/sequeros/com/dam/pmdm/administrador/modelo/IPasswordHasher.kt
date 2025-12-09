// Interfaz para bcrypt
package ies.sequeros.com.dam.pmdm.administrador.modelo

interface IPasswordHasher {
    fun hash(password: String): String
    fun verify(password: String, hash: String): Boolean
}