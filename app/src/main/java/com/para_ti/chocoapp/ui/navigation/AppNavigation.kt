package com.para_ti.chocoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.para_ti.chocoapp.domain.viewmodel.AuthViewModel
import com.para_ti.chocoapp.domain.viewmodel.ProfileViewModel
import com.para_ti.chocoapp.domain.viewmodel.CartViewModel
import com.para_ti.chocoapp.ui.adminView.AdminScreen
import com.para_ti.chocoapp.ui.cart.CartScreen
import com.para_ti.chocoapp.ui.home.HomeScreen
import com.para_ti.chocoapp.ui.login.LoginScreen
import com.para_ti.chocoapp.ui.profile.ProfileScreen
import com.para_ti.chocoapp.ui.register.RegisterScreen
import com.para_ti.chocoapp.ui.welcome.WelcomeScreen

object AppRoutes {
    const val WELCOME = "welcomeScreen"
    const val LOGIN = "loginScreen"
    const val REGISTER = "registerScreen"
    const val HOME = "homeScreen"
    const val ADMIN = "adminProductScreen"
    const val PROFILE = "profileScreen"
    const val CART = "cartScreen"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Los ViewModels se instancian aquí, lo cual está bien para el alcance del NavHost
    val authViewModel: AuthViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.WELCOME
    ) {

        // --- Pantalla de Bienvenida ---
        composable(AppRoutes.WELCOME) {
            WelcomeScreen(
                onNavigateToSignup = { navController.navigate(AppRoutes.REGISTER) },
                onNavigateToHome = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.WELCOME) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(AppRoutes.LOGIN) }
            )
        }

        // --- Pantalla de Login (Corregida) ---
        composable(AppRoutes.LOGIN) {
            // Nota: Asume que LoginScreen usa viewModel() internamente
            // o que se lo pasas como parámetro si es necesario.
            LoginScreen(
                // 1. Éxito de login de USUARIO
                onUserLogin = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.WELCOME) { inclusive = true }
                    }
                },
                // 2. Éxito de login de ADMIN
                onAdminLogin = {
                    navController.navigate(AppRoutes.ADMIN) {
                        popUpTo(AppRoutes.WELCOME) { inclusive = true }
                    }
                },
                // 3. Botón para ir a registrarse
                onNavigateToRegister = {
                    navController.navigate(AppRoutes.REGISTER)
                }
            )
        }

        // --- Pantalla de Registro (Corregida) ---
        composable(AppRoutes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.WELCOME) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(AppRoutes.REGISTER) { inclusive = true }
                    }
                }
            )
        }


        composable(AppRoutes.HOME) {

            HomeScreen(
                cartViewModel = cartViewModel, // ✅ Pasamos el ViewModel compartido
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(AppRoutes.WELCOME) {
                        popUpTo(AppRoutes.HOME) { inclusive = true }
                    }
                },
                onNavigateToCart = { navController.navigate(AppRoutes.CART) } // ✅ Navega al carrito
            )
        }
        // --- Pantalla de Admin ---
        composable(AppRoutes.ADMIN) {
            AdminScreen() // Asume que usa su propio ViewModel
        }

        // --- Pantalla de Perfil ---
        composable(AppRoutes.PROFILE) {
            ProfileScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(AppRoutes.WELCOME) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateToAdmin = { //   callback
                    navController.navigate(AppRoutes.ADMIN)
                },
                profileViewModel = profileViewModel
            )
        }

        // --- Pantalla de Carrito ---
        composable(AppRoutes.CART) {
            CartScreen(cartViewModel = cartViewModel)
        }
    }
}