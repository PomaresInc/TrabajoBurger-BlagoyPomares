package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.crear

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.ProductoDTO
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar.toDTO
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto
import ies.sequeros.com.dam.pmdm.administrador.modelo.IProductoRepositorio
import ies.sequeros.com.dam.pmdm.commons.infraestructura.AlmacenDatos
import ies.sequeros.com.dam.pmdm.generateUUID

class CrearProductoUseCase (private val repositorio: IProductoRepositorio,
                            private val almacenDatos: AlmacenDatos) {
    suspend fun invoke(createUserCommand: CrearProductoCommand): ProductoDTO {
        if (repositorio.findByName(createUserCommand.name)!=null) {
            throw IllegalArgumentException("El nombre ya esta registrado.")
        }

        val id = generateUUID()

        // Copiar la imagen al directorio de productos
        val nuevaImagePath = almacenDatos.copy(createUserCommand.imagePath, id, "/productos/")

        val item = Producto(
            id = id,
            name = createUserCommand.name,
            description = createUserCommand.description,
            imagePath = nuevaImagePath,
            price = createUserCommand.price,
            categoriaId = createUserCommand.categoriaId,
            enabled = createUserCommand.enabled
        )
        val element=repositorio.findByName(item.name)
        if (element!=null)
            throw IllegalArgumentException("El nombre ya esta registrado.")
        repositorio.add(item)
        return item.toDTO(almacenDatos.getAppDataDir()+"/productos/")



    }
}