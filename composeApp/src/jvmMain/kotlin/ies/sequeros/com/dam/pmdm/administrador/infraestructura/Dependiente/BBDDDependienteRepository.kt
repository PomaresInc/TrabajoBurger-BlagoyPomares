package ies.sequeros.com.dam.pmdm.administrador.infraestructura.Dependiente

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes.BBDDRepositorioDependientesJava
import ies.sequeros.com.dam.pmdm.administrador.modelo.Dependiente
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.administrador.modelo.IPasswordHasher

class BBDDDependienteRepository(
    private val bbddRepositorioDepedientesJava: BBDDRepositorioDependientesJava,
    // encriptador
    private val hasher: IPasswordHasher
) : IDependienteRepositorio {
    override suspend fun add(item: Dependiente) {
        //bbddRepositorioDepedientesJava.add(item)

        // crea una copia de dependiente cambiando la contraseña por su versión encriptada
        val dependienteEncriptado = item.copy(
            password = hasher.hash(item.password)
        )
        // Guardamos el objeto encriptado
        bbddRepositorioDepedientesJava.add(dependienteEncriptado)

    }

    override suspend fun remove(item: Dependiente): Boolean {
        bbddRepositorioDepedientesJava.remove(item)
        return true
    }
    override suspend fun remove(id: String): Boolean {

        bbddRepositorioDepedientesJava.remove(id)
        return true

    }

    override suspend fun update(item: Dependiente): Boolean {

        bbddRepositorioDepedientesJava.update(item)
        return true
    }

    override suspend fun getAll(): List<Dependiente> {

        return bbddRepositorioDepedientesJava.all
    }

    override suspend fun findByName(name: String): Dependiente? {

        return bbddRepositorioDepedientesJava.findByName( name)
    }
    override suspend fun getById(id: String): Dependiente? {
        return bbddRepositorioDepedientesJava.getById(id)
    }
}