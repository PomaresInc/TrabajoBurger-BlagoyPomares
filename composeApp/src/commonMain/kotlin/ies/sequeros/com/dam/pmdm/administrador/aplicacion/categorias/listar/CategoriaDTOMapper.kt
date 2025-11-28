package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar

import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria

fun Categoria.toDTO(path: String = "") = CategoriaDTO(
    id = id,
    name = name,
    description = description,
    imagePath = if (imagePath.isEmpty()) "" else path + imagePath,
    enabled = enabled
)

fun CategoriaDTO.toCategoria() = Categoria(
    id = id,
    name = name,
    description = description,
    imagePath = imagePath,
    enabled = enabled
)