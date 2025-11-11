package com.para_ti.chocoapp.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.para_ti.chocoapp.R
import com.para_ti.chocoapp.ui.theme.ChocolateBrown
import com.para_ti.chocoapp.ui.theme.Cream
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    onNavigateToSignup: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var isCheckingAuth by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        delay(1000)
        if (user != null) {
            onNavigateToHome()
        } else {
            isCheckingAuth = false
        }
    }

    if (isCheckingAuth) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ChocolateBrown),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Cream)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ChocolateBrown)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
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
                    Image(
                        painter = painterResource(id = R.drawable.logo_para_ti),
                        contentDescription = "Logo Chocolates Para Ti",
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

            // 🔹 Cambiamos la acción: si no está logeado, va al login
            Button(
                onClick = { onNavigateToLogin() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Cream,
                    contentColor = ChocolateBrown
                ),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Compra ya")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onNavigateToSignup() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Cream,
                    contentColor = ChocolateBrown
                ),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Crea una cuenta")
            }
        }
    }
}
