package ies.sequeros.com.dam.pmdm.tpv.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TPVCliente(
    viewModel: TPVClienteViewModel,
    onContinuar: (String) -> Unit
) {
    val clientName by viewModel.clientName.collectAsState()
    val clientNameError by viewModel.clientNameError.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenido a VegaBurguer",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Por favor, introduce tu nombre para continuar",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = clientName,
            onValueChange = { viewModel.onClientNameChange(it) },
            label = { Text("Nombre del cliente") },
            isError = clientNameError != null,
            supportingText = {
                clientNameError?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(bottom = 24.dp),
            singleLine = true
        )

        Button(
            onClick = {
                if (viewModel.isValid()) {
                    onContinuar(clientName)
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(56.dp),
            enabled = clientName.isNotBlank()
        ) {
            Text(
                text = "Continuar",
                fontSize = 18.sp
            )
        }
    }
}
