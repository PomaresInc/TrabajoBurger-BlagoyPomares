package ies.sequeros.com.dam.pmdm.administrador.ui.pedidos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedido.listar.PedidoDTO
import ies.sequeros.com.dam.pmdm.administrador.ui.MainAdministradorViewModel

@Composable
fun Pedidos(
    mainAdministradorViewModel: MainAdministradorViewModel,
    pedidosViewModel: PedidosViewModel,
    onSelectItem: (PedidoDTO?) -> Unit
) {
    // Actualizar la lista cuando se muestra la pantalla
    LaunchedEffect(Unit) {
        pedidosViewModel.refresh()
    }
    
    val items by pedidosViewModel.items.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val filteredItems = items.filter {
        if (searchText.isNotBlank()) {
            it.client_name.contains(searchText, ignoreCase = true) ||
            it.fecha.contains(searchText, ignoreCase = true) ||
            it.id.contains(searchText, ignoreCase = true)
        } else {
            true
        }
    }

    // Contenedor principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Barra superior con buscador y botón refrescar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Buscar pedidos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            Spacer(Modifier.width(8.dp))
            OutlinedButton(
                onClick = {
                    pedidosViewModel.refresh()
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refrescar"
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 512.dp)
        ) {
            items(filteredItems.size) { index ->
                PedidoCard(
                    filteredItems[index],
                    onView = {
                        pedidosViewModel.setSelectedPedido(filteredItems[index])
                        onSelectItem(filteredItems[index])
                    }
                )
            }
        }
    }
}
