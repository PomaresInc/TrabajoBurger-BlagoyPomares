package ies.sequeros.com.dam.pmdm.tpv.carrito

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TPVCarrito(
    clientName: String,
    items: List<TPVCarritoItem>,
    total: Double,
    onCantidadChange: (String, Int) -> Unit,
    onEliminar: (String) -> Unit,
    onConfirmarPedido: () -> Unit,
    onCancelarPedido: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Detalle del Pedido")
                        Text(
                            "Cliente: $clientName",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                shadowElevation = 8.dp,
                tonalElevation = 3.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "%.2f€".format(total),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = onCancelarPedido,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text(
                                text = "Cancelar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Button(
                            onClick = onConfirmarPedido,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            enabled = items.isNotEmpty()
                        ) {
                            Text(
                                text = "Confirmar Pedido",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Tu carrito está vacío",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items, key = { it.productoId }) { item ->
                    CarritoItemCard(
                        item = item,
                        onCantidadChange = { onCantidadChange(item.productoId, it) },
                        onEliminar = { onEliminar(item.productoId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CarritoItemCard(
    item: TPVCarritoItem,
    onCantidadChange: (Int) -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("%.2f€".format(item.precio), fontSize = 14.sp)
                Text(
                    "Subtotal: %.2f€".format(item.subtotal),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onCantidadChange(item.cantidad - 1) }) {
                    Icon(Icons.Default.Remove, "Reducir")
                }
                Text(
                    "${item.cantidad}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                IconButton(onClick = { onCantidadChange(item.cantidad + 1) }) {
                    Icon(Icons.Default.Add, "Aumentar")
                }
                IconButton(onClick = onEliminar) {
                    Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
