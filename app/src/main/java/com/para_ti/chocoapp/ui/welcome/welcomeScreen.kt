package com.para_ti.chocoapp.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.para_ti.chocoapp.R
import com.para_ti.chocoapp.ui.theme.ChocolateBrown
import com.para_ti.chocoapp.ui.theme.Cream
import com.para_ti.chocoapp.ui.theme.Parati_chocolate_appTheme


// --- CORRECCIÓN 1: Añadir el nuevo parámetro de navegación ---
@Composable
fun WelcomeScreen(
    onNavigateToSignup: () -> Unit,
    onNavigateToHome: () -> Unit // Parámetro añadido para el inicio de sesión/compra directa
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChocolateBrown)
            .padding(16.dp), // Añadido un padding general para que los botones no toquen los bordes
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier=Modifier.height(64.dp))
        Card(
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = ChocolateBrown)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image (
                    painter = painterResource(id = R.drawable.logo_para_ti),
                    contentDescription = "Chocolate box",
                    modifier = Modifier.size(200.dp)
                )
            }
        }

        Text(
            text = "Chocolates Para Ti",
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Momentos que se convierten en recuerdos eternos.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Cream),
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(60.dp))

        // --- CORRECCIÓN 2: Asignar la acción correcta a este botón ---
        // Este botón ahora navega a la pantalla principal (Home).
        Button(
            onClick = { onNavigateToHome() }, // Acción cambiada
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Cream,
                contentColor = ChocolateBrown
            )
        ){
            Text(
                text = "Compra ya",
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Este botón mantiene la acción de navegar a la pantalla de registro.
        Button(
            onClick = { onNavigateToSignup() }, // Acción se mantiene
            modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = Cream,
                contentColor = ChocolateBrown
            )
        ) {
            Text("Crea una cuenta")
        }
    }
}

// ---
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    Parati_chocolate_appTheme {
        // Pasamos una acción vacía para el nuevo parámetro en el preview.
        WelcomeScreen(
            onNavigateToSignup = {},
            onNavigateToHome = {} // Parámetro añadido
        )
    }
}
