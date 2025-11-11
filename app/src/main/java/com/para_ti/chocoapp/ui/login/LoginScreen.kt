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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.para_ti.chocoapp.R
import com.para_ti.chocoapp.domain.viewmodel.AuthViewModel
import com.para_ti.chocoapp.domain.viewmodel.AuthState
import com.para_ti.chocoapp.ui.theme.ChocolateBrown
import com.para_ti.chocoapp.ui.theme.Cream
import com.para_ti.chocoapp.ui.theme.Parati_chocolate_appTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
@Composable
fun LoginScreen(
    onUserLogin: () -> Unit,           // Navegar al Home
    onAdminLogin: () -> Unit,          // Navegar al panel admin
    onNavigateToRegister: () -> Unit,  // Navegar a registro
    viewModel: AuthViewModel = viewModel()
) {
    // Estados locales para el formulario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Estado para controlar si estamos cargando (para evitar múltiples clics)
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()

    // Definición de colores locales si no los tienes importados globalmente
    // (Ajusta estos valores a los de tu tema si es necesario)
    val ChocolateBrown = Color(0xFF3E2723)
    val Cream = Color(0xFFFFFDD0)

    // Manejo de Efectos Secundarios (Navegación y Errores)
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                isLoading = true
                val currentUser = FirebaseAuth.getInstance().currentUser
                currentUser?.let { user ->
                    FirebaseFirestore.getInstance()
                        .collection("usuarios")
                        .document(user.uid)
                        .get()
                        .addOnSuccessListener { doc ->
                            isLoading = false
                            val rol = doc.getString("rol")
                            if (rol == "admin") {
                                onAdminLogin()
                            } else {
                                onUserLogin()
                            }
                        }
                        .addOnFailureListener {
                            isLoading = false
                            Toast.makeText(context, "Error al verificar permisos", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            is AuthState.Error -> {
                isLoading = false
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            is AuthState.Loading -> {
                isLoading = true
            }
            else -> {
                isLoading = false
            }
        }
    }

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

        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo", color = Cream) },
            singleLine = true,
            enabled = !isLoading, // Deshabilita si está cargando
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Cream,
                unfocusedBorderColor = Cream.copy(alpha = 0.7f),
                cursorColor = Cream,
                focusedLabelColor = Cream,
                unfocusedLabelColor = Cream
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Campo Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = Cream) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            enabled = !isLoading,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Cream,
                unfocusedBorderColor = Cream.copy(alpha = 0.7f),
                cursorColor = Cream,
                focusedLabelColor = Cream,
                unfocusedLabelColor = Cream
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Botón de Login
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.login(email, password)
                } else {
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Cream,
                contentColor = ChocolateBrown,
                disabledContainerColor = Cream.copy(alpha = 0.5f)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = ChocolateBrown,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Iniciar sesión")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Ir a Registro
        TextButton(onClick = { onNavigateToRegister() }) {
            Text(
                text = "¿No tienes una cuenta? Regístrate",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() { Parati_chocolate_appTheme { Surface(modifier = Modifier.fillMaxSize()) { LoginScreen( onUserLogin = {}, onNavigateToRegister = {}, isPreview = true ) } } }