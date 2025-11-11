package com.para_ti.chocoapp.ui.register

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.para_ti.chocoapp.R
import com.para_ti.chocoapp.domain.viewmodel.AuthViewModel
import com.para_ti.chocoapp.domain.viewmodel.AuthState
import com.para_ti.chocoapp.ui.theme.ChocolateBrown
import com.para_ti.chocoapp.ui.theme.Cream
import com.para_ti.chocoapp.ui.theme.Parati_chocolate_appTheme

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,   // Ir al Home después de registrarse
    onNavigateToLogin: () -> Unit,   // Volver al login
    isAdminRegistering: Boolean = false,
    isPreview: Boolean = false
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("usuario") } // rol por defecto

    val context = LocalContext.current
    val viewModel: AuthViewModel? = if (!isPreview) viewModel() else null
    val authState by viewModel?.authState?.collectAsState() ?: remember { mutableStateOf(AuthState.Idle) }


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
        // Aquí tu logo y textos
        // ... (igual que antes)

        // Input fields
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo", color = Cream) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico", color = Cream) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = Cream) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña", color = Cream) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Selección de rol solo si es un admin registrando otro usuario
        if (isAdminRegistering) {
            Text("Rol del usuario:", color = Cream)
            Row {
                RadioButton(
                    selected = selectedRole == "usuario",
                    onClick = { selectedRole = "usuario" },
                    colors = RadioButtonDefaults.colors(selectedColor = Cream)
                )
                Text("Usuario", color = Cream, modifier = Modifier.padding(end = 16.dp))
                RadioButton(
                    selected = selectedRole == "admin",
                    onClick = { selectedRole = "admin" },
                    colors = RadioButtonDefaults.colors(selectedColor = Cream)
                )
                Text("Admin", color = Cream)
            }
            Spacer(Modifier.height(16.dp))
        }

        Button(
            onClick = {
                if (!isPreview) {
                    if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                    } else if (password != confirmPassword) {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel?.register(nombre,email, password, rol = selectedRole) // Usa el rol seleccionado
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Cream)
        ) {
            Text("Registrar cuenta", color = ChocolateBrown)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            // redirige directamente al login
            onNavigateToLogin()
        }) {
            Text("¿Ya tienes cuenta? Inicia sesión", color = Cream)
        }


    }

    // Manejo de authState seguro con LaunchedEffect
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                val currentUser = FirebaseAuth.getInstance().currentUser
                currentUser?.let { user ->
                    val db = FirebaseFirestore.getInstance()
                    val userData = hashMapOf(
                        "nombre" to nombre,
                        "correo" to email,
                        "rol" to selectedRole
                    )
                    db.collection("usuarios").document(user.uid)
                        .set(userData)
                        .addOnSuccessListener { onRegisterSuccess() }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error al guardar usuario", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    Parati_chocolate_appTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RegisterScreen(onRegisterSuccess = {},onNavigateToLogin = {}, isPreview = true)
        }
    }
}
