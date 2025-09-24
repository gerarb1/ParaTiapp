package com.para_ti.chocoapp.ui.login.ui
import com.para_ti.chocoapp.ui.theme.Parati_chocolate_appTheme
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.para_ti.chocolate.ui.theme.ChocolateBrown
import com.para_ti.chocolate.ui.theme.Cream
import com.para_ti.chocolate.ui.theme.DarkBackground
import androidx.compose.ui.tooling.preview.Preview
@Composable
fun LoginScreen(onBack: () -> Unit) {
    // Estados para los campos del formulario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Icono temporal
        Card(
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = ChocolateBrown)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "☕",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 60.sp)
                )
            }
        }

        Text(
            text = "Para Ti Chocolate",
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Indulge in our premium chocolates!",
            style = MaterialTheme.typography.bodyMedium.copy(color = Cream),
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        // Campos del formulario
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Cream) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = ChocolateBrown,
                unfocusedBorderColor = Cream.copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Cream) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = ChocolateBrown,
                unfocusedBorderColor = Cream.copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name", color = Cream) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = ChocolateBrown,
                unfocusedBorderColor = Cream.copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name", color = Cream) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = ChocolateBrown,
                unfocusedBorderColor = Cream.copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = mobileNumber,
            onValueChange = { mobileNumber = it },
            label = { Text("Mobile Number", color = Cream) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = ChocolateBrown,
                unfocusedBorderColor = Cream.copy(alpha = 0.7f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // TODO: Registrar usuario
                println("Registrando usuario: $email")
            },
            colors = ButtonDefaults.buttonColors(containerColor = ChocolateBrown),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Join Now",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { onBack() }
        ) {
            Text(
                text = "Already have an account? Sign in",
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
            LoginScreen(onBack = {})
        }
    }
}


