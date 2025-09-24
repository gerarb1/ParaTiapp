package com.para_ti.chocoapp.ui.login.ui

import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.para_ti.chocoapp.R
import com.para_ti.chocoapp.ui.theme.ChocolateBrown
import com.para_ti.chocoapp.ui.theme.Cream
import com.para_ti.chocoapp.ui.theme.DarkBackground
import com.para_ti.chocoapp.ui.theme.Parati_chocolate_appTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WelcomeScreen(onNavigateToSignup: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen del chocolate - Si no tienes la imagen, comentar estas líneas
        /*
        Image(
            painter = painterResource(id = R.drawable.chocolate_box),
            contentDescription = "Chocolate box",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp)
        )
        */

        // Mientras tanto, usamos un icono temporal
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
                Text(
                    text = "🍫",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 80.sp),
                    textAlign = TextAlign.Center
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

        Button(
            onClick = { /* TODO: Ir al catálogo */ },
            colors = ButtonDefaults.buttonColors(containerColor = ChocolateBrown),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Shop now",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { onNavigateToSignup() },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Cream
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crea una cuenta")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    Parati_chocolate_appTheme {
        WelcomeScreen(onNavigateToSignup = {})
    }
}

