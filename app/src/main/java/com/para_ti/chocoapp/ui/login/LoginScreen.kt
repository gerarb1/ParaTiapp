package com.para_ti.chocoapp.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.para_ti.chocoapp.R
import com.para_ti.chocoapp.data.local.AppDatabase
import com.para_ti.chocoapp.ui.theme.ChocolateBrown
import com.para_ti.chocoapp.ui.theme.Cream
import com.para_ti.chocoapp.ui.theme.Parati_chocolate_appTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onSignupSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    isPreview: Boolean = false
) {
    // Estados del formulario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val userDao = db.userDao()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChocolateBrown)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Logo
        Card(
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = ChocolateBrown)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.logo_para_ti),
                    contentDescription = "Logo",
                    modifier = Modifier.size(200.dp)
                )
            }
        }

        Text(
            text = "Momentos que se convierten en recuerdos eternos.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Cream),
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        // Campo de correo
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Nombre", color = Cream) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Cream,
                unfocusedBorderColor = Cream.copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = Cream) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Cream,
                unfocusedBorderColor = Cream.copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Botón de inicio de sesión
        Button(
            onClick = {
                coroutineScope.launch {
                    val user = userDao.login(email, password)
                    if (user != null) {
                        Toast.makeText(context, "Bienvenido ${user.username}", Toast.LENGTH_SHORT).show()
                        onSignupSuccess()
                    } else {
                        Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Cream,
                contentColor = ChocolateBrown
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { onNavigateBack() }) {
            Text(
                text = "¿No tienes cuenta? Regístrate",
                color = Cream
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Parati_chocolate_appTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LoginScreen(
                onSignupSuccess = {},
                onNavigateBack = {},
                isPreview = true
            )
        }
    }
}
