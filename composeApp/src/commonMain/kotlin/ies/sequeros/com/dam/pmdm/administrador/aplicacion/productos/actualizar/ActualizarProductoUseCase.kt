package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.actualizar

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos

import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio


class ActualizarProductoUseCase (private val repositorio: IProductoRepositorio,
                                 private val almacenDatos: AlmacenDatos) {

    suspend fun invoke(command: ActualizarProductoCommand, ): ProductoDTO {

        val item: Producto?=repositorio.getById(command.id)

        var nuevaImagePath:String?=null
        if (item==null) {
            throw IllegalArgumentException("El usuario no esta registrado.")
        }

        var itemDTO: ProductoDTO=item.toDTO(almacenDatos.getAppDataDir()+"/productos/")

        if (itemDTO.imagePath!=command.imagePath) {
            almacenDatos.remove(itemDTO.imagePath)
            nuevaImagePath = almacenDatos.copy(command.imagePath, command.id, "/productos/")
        }else{
            nuevaImagePath=item.imagePath
        }

        var newProducto= item.copy(
            name=command.name,
            description = command.description,
            imagePath = nuevaImagePath,
            enabled = command.enabled
        )

        repositorio.update(newProducto)

        return newProducto.toDTO(almacenDatos.getAppDataDir()+"/productos/")

    }
}