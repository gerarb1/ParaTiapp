package com.para_ti.chocoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.para_ti.chocoapp.ui.home.HomeScreen
import com.para_ti.chocoapp.ui.login.LoginScreen
import com.para_ti.chocoapp.ui.welcome.WelcomeScreen

// Define las "rutas" como constantes para evitar errores de tipeo
object AppRoutes {
    const val WELCOME = "welcomeScreen"
    const val SIGNUP = "LoginScreen"
    const val HOME = "HomeScreen"
}

@Composable
fun AppNavigation() {
    // 1. Crea un NavController: este objeto controla la navegación
    val navController = rememberNavController()

    // 2. Define el NavHost: este es el contenedor donde se mostrarán tus pantallas
    NavHost(
        navController = navController,
        startDestination = AppRoutes.WELCOME // 3. Define la pantalla inicial
    ) {
        // 4. Define cada pantalla (composable) de tu aplicación
        composable(route = AppRoutes.WELCOME) {
            WelcomeScreen(
                // Define las acciones de navegación
                onNavigateToSignup = { navController.navigate(AppRoutes.SIGNUP) },
                onNavigateToHome = {
                    // Navega a Home y limpia el historial para que el usuario no pueda volver atrás con el botón de retroceso
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.WELCOME) { inclusive = true }
                    }
                }
            )
        }

        composable(route = AppRoutes.SIGNUP) {
            LoginScreen(
                onSignupSuccess = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.WELCOME) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = AppRoutes.HOME) {
            HomeScreen(
                // Aquí podrías añadir más callbacks de navegación si HomeScreen necesita ir a otros lugares
            )
        }
    }
}
