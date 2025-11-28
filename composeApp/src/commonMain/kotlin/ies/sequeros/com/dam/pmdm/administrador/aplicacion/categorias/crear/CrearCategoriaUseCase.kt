package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.crear

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID


class CrearCategoriaUseCase(private val repositorio: ICategoriaRepositorio, private val AlmacenDatos:AlmacenDatos) {

    suspend fun invoke(createUserCommand: CrearCategoriaCommand): CategoriaDTO {
    if (repositorio.findByName(createUserCommand.name)!=null) {
        throw IllegalArgumentException("El nombre ya esta registrado.")
    }
        val id = generateUUID()

        val item = Categoria(
            id = id,
            name = createUserCommand.name,
            description = createUserCommand.description,
            imagePath = createUserCommand.imagePath,
            enabled = createUserCommand.enabled
        )
        val element=repositorio.findByName(item.name)
        if (element!=null)
            throw IllegalArgumentException("El nombre ya esta registrado.")
        repositorio.add(item)
        return item.toDTO(AlmacenDatos.getAppDataDir()+"/categorias/")
    }
}