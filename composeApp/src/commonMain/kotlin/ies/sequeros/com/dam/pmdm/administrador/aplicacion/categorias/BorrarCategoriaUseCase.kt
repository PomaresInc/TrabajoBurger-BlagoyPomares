package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.ICategoriaRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

class BorrarCategoriaUseCase (private val repositorio: ICategoriaRepositorio, private val almacenDatos: AlmacenDatos){

        suspend fun invoke(id: String){
            val tempo=repositorio.getById(id)

            if (tempo==null) {
                throw IllegalArgumentException("El id de categoria no exixte.")
            }
            val tempoDto=tempo.toDTO(almacenDatos.getAppDataDir()+"/categorias/")

            repositorio.remove(id)

            almacenDatos.remove(tempoDto.imagePath)
        }

}