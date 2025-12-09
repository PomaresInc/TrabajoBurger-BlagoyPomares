// Clase para bcrypt (desktop)
package ies.sequeros.com.dam.pmdm.administrador.infraestructura.util

import ies.sequeros.com.dam.pmdm.administrador.modelo.IPasswordHasher
import org.mindrot.jbcrypt.BCrypt

class BcryptPasswordHasher : IPasswordHasher {

    override fun hash(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    override fun verify(password: String, hash: String): Boolean {
        return try {
            BCrypt.checkpw(password, hash)
        } catch (e: Exception) {
            false
        }
    }

}