package com.para_ti.chocoapp.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.para_ti.chocoapp.domain.viewmodel.ProfileViewModel
import com.para_ti.chocoapp.ui.theme.Cream
import com.para_ti.chocoapp.ui.theme.ChocolateBrown

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onNavigateToAdmin: () -> Unit, //  nuevo parámetro para navegar
    profileViewModel: ProfileViewModel = viewModel()
) {
    val userProfile by profileViewModel.userProfile.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.loadUserProfile()
    }

    userProfile?.let { profile ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Perfil de Usuario",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = ChocolateBrown
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Nombre: ${profile.name}", fontSize = 18.sp)
            Text(text = "Correo: ${profile.email}", fontSize = 18.sp)
            Text(text = "Rol: ${profile.rol}", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(32.dp))

            // Mostrar botón de Panel Admin solo si el rol es "admin"
            if (profile.rol.equals("admin", ignoreCase = true)) {
                Button(
                    onClick = onNavigateToAdmin,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ChocolateBrown,
                        contentColor = Cream
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Text("Panel de Administración")
                }
            }

            // 🔹 Botón para cerrar sesión
            Button(
                onClick = {
                    profileViewModel.logout()
                    onLogout()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ChocolateBrown,
                    contentColor = Cream
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = ChocolateBrown)
    }
}
