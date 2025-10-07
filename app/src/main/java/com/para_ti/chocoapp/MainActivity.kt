package com.para_ti.chocoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.para_ti.chocoapp.ui.navigation.AppNavigation // Importa el grafo de navegación que crearemos
import com.para_ti.chocoapp.ui.theme.Parati_chocolate_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el modo de pantalla completa (edge-to-edge)
        setContent {
            // Aplica el tema de tu app
            Parati_chocolate_appTheme {
                // Llama al sistema de navegación, que se encargará de mostrar la pantalla correcta
                AppNavigation()
            }
        }
    }
}
