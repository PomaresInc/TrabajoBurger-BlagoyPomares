package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.actualizar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class ActualizarCategoriaUseCase(private val repositorio: ICategoriaRepositorio,
                                 private val almacenDatos: AlmacenDatos
) {

    suspend fun invoke(command: ActualizarCategoriaCommand, ): CategoriaDTO {

        val item: Categoria?=repositorio.getById(command.id)

        var nuevaImagePath:String?=null
        if (item==null) {
            throw IllegalArgumentException("La Categoria no existe.")
        }

        var itemDTO: CategoriaDTO=item.toDTO(almacenDatos.getAppDataDir()+"/categorias/")

        if(itemDTO.imagePath!=command.imagePath) {
            almacenDatos.remove(itemDTO.imagePath)
            nuevaImagePath = almacenDatos.copy(command.imagePath, command.id, "/categorias/")
        }else{
            nuevaImagePath=item.imagePath
        }

        var newCategoria= item.copy(
            name=command.name,
            description = command.description,
            imagePath = nuevaImagePath,
            enabled = command.enabled
        )
        repositorio.update(newCategoria)

        return newCategoria.toDTO(almacenDatos.getAppDataDir()+"/categorias/")

    }

}