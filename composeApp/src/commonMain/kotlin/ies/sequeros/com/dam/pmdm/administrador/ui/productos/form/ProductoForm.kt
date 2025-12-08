package ies.sequeros.com.dam.pmdm.administrador.ui.productos.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EuroSymbol
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// Importamos el DTO de Categoría
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.listar.CategoriaDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.productos.ProductosViewModel
import ies.sequeros.com.dam.pmdm.commons.ui.ImagenDesdePath
import ies.sequeros.com.dam.pmdm.commons.ui.SelectorImagenComposable

import vegaburguer.composeapp.generated.resources.Res
import vegaburguer.composeapp.generated.resources.hombre

@Composable
fun ProductoForm (
    productosViewModel: ProductosViewModel,
    onClose: () -> Unit,
    onConfirm: (datos: ProductoFormState) -> Unit = {},
    productoFormularioViewModel: ProductoFormViewModel = viewModel {
        ProductoFormViewModel (
            productosViewModel.selected.value, onConfirm
        )
    }
) {
    val state by productoFormularioViewModel.uiState.collectAsState()
    val formValid by productoFormularioViewModel.isFormValid.collectAsState()
    val selected = productosViewModel.selected.collectAsState()

    // Obtener categorias del viewmodel
    val categoriasDisponibles by productosViewModel.categorias.collectAsState()

    val imagePath =
        remember { mutableStateOf(if (state.imagePath != null && state.imagePath.isNotEmpty()) state.imagePath else "") }

    // Scroll state
    val scrollState = rememberScrollState()

    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .defaultMinSize(minHeight = 200.dp),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(scrollState), // scroll vertical
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //  Título
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = if (selected.value == null)
                        "Añadir producto"
                    else
                        "Editar producto",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Nombre
            OutlinedTextField(
                value = state.name,
                onValueChange = { productoFormularioViewModel.onNombreChange(it) },
                label = { Text("Nombre del producto") },
                leadingIcon = { Icon(Icons.Default.Label, contentDescription = null) },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            state.nombreError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Precio
            OutlinedTextField(
                value = state.price,
                onValueChange = {
                    productoFormularioViewModel.onPriceChange(it)
                },
                label = { Text("Precio (€)") },
                leadingIcon = { Icon(Icons.Default.EuroSymbol, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            // Descripción
            OutlinedTextField(
                value = state.description,
                onValueChange = {
                    productoFormularioViewModel.onDescriptionChange(it)
                },
                label = { Text("Descripción") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )


            // Buscamos el objeto categoría completo que coincida con el ID guardado en el estado
            val currentCategoria = categoriasDisponibles.find { it.id == state.categoria }

            CategoriasComboBox(
                categorias = categoriasDisponibles,
                current = currentCategoria,
                onSelect = { nuevaCategoria ->
                    // Llamamos al viewModel del formulario para actualizar el ID
                    productoFormularioViewModel.onCategoriaChange(nuevaCategoria.id)
                }
            )

            // Checkboxes
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = state.enabled,
                        onCheckedChange = { productoFormularioViewModel.onEnabledChange(it) }
                    )
                    Text("Producto Activo", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Text("Imagen del producto:", style = MaterialTheme.typography.titleSmall)

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            SelectorImagenComposable({ it: String ->
                productoFormularioViewModel.onImagePathChange(it)
                imagePath.value = it
            })

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            ImagenDesdePath(imagePath, Res.drawable.hombre, Modifier.fillMaxSize())

            state.imagePathError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            //  Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(onClick = { productoFormularioViewModel.clear() }) {
                    Icon(Icons.Default.Autorenew, contentDescription = null)
                }

                Button(
                    onClick = {
                        productoFormularioViewModel.submit(
                            onSuccess = {
                                onConfirm(productoFormularioViewModel.uiState.value)
                            },
                            onFailure = {}
                        )
                    },
                    enabled = formValid
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                }

                FilledTonalButton(onClick = { onClose() }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }
        }
    }
}

// Combobox
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriasComboBox(
    categorias: List<CategoriaDTO>,
    current: CategoriaDTO?,
    onSelect: (CategoriaDTO) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val displayText = current?.name ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = displayText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Categoría") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize()
        ) {
            categorias.forEach { categoria ->
                DropdownMenuItem(
                    text = { Text(categoria.name) },
                    onClick = {
                        onSelect(categoria)
                        expanded = false
                    }
                )
            }
        }
    }
}