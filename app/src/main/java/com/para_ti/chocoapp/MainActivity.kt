package com.para_ti.chocoapp

//clodinary


import android.net.Uri;
//import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

//import com.cloudinary.cloudinaryquickstart.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.para_ti.chocoapp.data.firebase.seedProductos
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
            //seedProductos()

            setContent {
                Parati_chocolate_appTheme {
                    AppNavigation(
                        // Puedes pasar argumentos aquí si es necesario
                    )
                }
            }
        }
    }
}
