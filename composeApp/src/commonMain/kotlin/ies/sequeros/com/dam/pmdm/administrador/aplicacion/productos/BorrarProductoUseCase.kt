package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos

import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO

class BorrarProductoUseCase(private val repositorio: IProductoRepositorio, private val almacenDatos: AlmacenDatos) {

    suspend fun invoke(id: String) {
        val tempo=repositorio.findByProductoId(id)

        if (tempo==null) {
            throw IllegalArgumentException("El id no está registrado.")
        }

        val tempoDto=tempo.toDTO(almacenDatos.getAppDataDir()+"/productos/")

        repositorio.remove(id)

        almacenDatos.remove(tempoDto.imagePath)

    }
}