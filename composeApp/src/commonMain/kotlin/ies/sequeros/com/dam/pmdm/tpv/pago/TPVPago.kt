package ies.sequeros.com.dam.pmdm.tpv.pago

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TPVPago(
    clientName: String,
    total: Double,
    onFinalizarPedido: () -> Unit,
    onCancelar: () -> Unit,
    onBack: () -> Unit
) {
    var selectedPaymentMethod by remember { mutableStateOf("Efectivo") }
    val paymentMethods = listOf("Efectivo", "Tarjeta de Crédito", "Tarjeta de Débito", "Bizum", "Transferencia")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Opciones de Pago")
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
                        Text("Total a pagar:", fontSize = 24.sp, fontWeight = FontWeight.Bold)
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
                            onClick = onCancelar,
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
                            onClick = onFinalizarPedido,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                        ) {
                            Text(
                                text = "Finalizar Pedido",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selecciona el método de pago",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    paymentMethods.forEach { method ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedPaymentMethod == method,
                                    onClick = { selectedPaymentMethod = method }
                                )
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedPaymentMethod == method,
                                onClick = { selectedPaymentMethod = method }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = method,
                                fontSize = 18.sp,
                                fontWeight = if (selectedPaymentMethod == method) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                        if (method != paymentMethods.last()) {
                            Divider()
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (selectedPaymentMethod.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Método seleccionado:",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = selectedPaymentMethod,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}
