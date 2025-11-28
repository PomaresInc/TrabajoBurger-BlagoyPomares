package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.activar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ActivarCategoriaUserCase(private val repositorio: ICategoriaRepositorio, private val almacenDatos: AlmacenDatos){

    suspend fun invoke(command: ActivarCategoriaCommand ): CategoriaDTO{

        val item: Categoria?=repositorio.getById(command.id)
        if (item==null) {
            throw IllegalArgumentException("La Categoria no existe.")
        }
        var newCategoria= item.copy(
            enabled = command.enabled,
        )
        repositorio.update(newCategoria)

        return newCategoria.toDTO(almacenDatos.getAppDataDir()+"/categorias/")
    }
}