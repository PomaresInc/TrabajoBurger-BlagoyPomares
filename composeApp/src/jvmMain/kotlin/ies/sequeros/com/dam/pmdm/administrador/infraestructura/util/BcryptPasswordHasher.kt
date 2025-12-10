// Clase para bcrypt (desktop)
package ies.sequeros.com.dam.pmdm.administrador.infraestructura.util

import ies.sequeros.com.dam.pmdm.administrador.modelo.IPasswordHasher
import org.mindrot.jbcrypt.BCrypt

import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import java.math.BigInteger

class BcryptPasswordHasher : IPasswordHasher {
/*
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
*/
    override fun hash(password: String): String {
        val iterations = 1000
        val salt = getSalt()
        val hash = hashPassword(password.toCharArray(), salt, iterations)
        return "$iterations:${toHex(salt)}:${toHex(hash)}"
    }

    override fun verify(password: String, hash: String): Boolean {
        val parts = hash.split(":")
        if (parts.size != 3) return false
        try {
            val iterations = parts[0].toInt()
            val salt = fromHex(parts[1])
            val storedHash = fromHex(parts[2])
            val incomingHash = hashPassword(password.toCharArray(), salt, iterations)
            return validatePassword(incomingHash, storedHash)
        } catch (e: Exception) {
            return false
        }
    }

    private fun getSalt(): ByteArray {
        val sr = SecureRandom.getInstance("SHA1PRNG")
        val salt = ByteArray(16)
        sr.nextBytes(salt)
        return salt
    }

    private fun hashPassword(password: CharArray, salt: ByteArray, iterations: Int): ByteArray {
        try {
            val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec = PBEKeySpec(password, salt, iterations, 64 * 8)
            return skf.generateSecret(spec).encoded
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun validatePassword(original: ByteArray, stored: ByteArray): Boolean {
        if (original.size != stored.size) return false
        var diff = 0
        for (i in original.indices) {
            diff = diff or (original[i].toInt() xor stored[i].toInt())
        }
        return diff == 0
    }

    private fun toHex(array: ByteArray): String {
        val bi = BigInteger(1, array)
        val hex = bi.toString(16)
        val paddingLength = (array.size * 2) - hex.length
        return if (paddingLength > 0) String.format("%0" + paddingLength + "d", 0) + hex else hex
    }

    private fun fromHex(hex: String): ByteArray {
        val binary = ByteArray(hex.length / 2)
        for (i in binary.indices) {
            binary[i] = ((Character.digit(hex[2 * i], 16) shl 4) +
                    Character.digit(hex[2 * i + 1], 16)).toByte()
        }
        return binary
    }

}