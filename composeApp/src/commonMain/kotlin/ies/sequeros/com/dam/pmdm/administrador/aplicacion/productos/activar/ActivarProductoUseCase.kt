package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.activar


import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio



class ActivarProductoUseCase (private val repositorio: IProductoRepositorio, private val almacenDatos: AlmacenDatos) {

    suspend fun invoke(command: ActivarProductoCommand ): ProductoDTO {
        val item: Producto?=repositorio.getById(command.id)
        if (item==null) {
            throw IllegalArgumentException("El usuario no esta registrado.")
        }
        var newUser= item.copy(
            enabled = command.enabled,
        )
        repositorio.update(newUser)

        return newUser.toDTO(almacenDatos.getAppDataDir()+"/productos/")

    }
}