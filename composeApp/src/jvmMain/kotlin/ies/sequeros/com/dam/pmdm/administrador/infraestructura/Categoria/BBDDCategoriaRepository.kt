package ies.sequeros.com.dam.pmdm.administrador.infraestructura.Categoria

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias.BBDDRepositorioCategoriaJava
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio


class BBDDCategoriaRepository (
    private val bbddRepositorioCategoriaJava: BBDDRepositorioCategoriaJava) : ICategoriaRepositorio {
    override suspend fun add(item: Categoria) {
        bbddRepositorioCategoriaJava.add(item)
    }

    override suspend fun remove(id: String): Boolean {
        bbddRepositorioCategoriaJava.remove(id)
        return true
    }

    override suspend fun findByName(name: String): Categoria? {
        return bbddRepositorioCategoriaJava.findByName( name)
    }

    override suspend fun getById(id: String): Categoria? {
        return bbddRepositorioCategoriaJava.getById(id)
    }

    override suspend fun getAll(): List<Categoria> {
        return bbddRepositorioCategoriaJava.getAll()
    }

    override suspend fun update(item: Categoria): Boolean {
        bbddRepositorioCategoriaJava.update(item)
        return true

    }

}