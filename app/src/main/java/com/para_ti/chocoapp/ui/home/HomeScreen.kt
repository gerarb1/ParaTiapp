package com.para_ti.chocoapp.ui.home // Considera mover HomeScreen a un paquete como com.para_ti.chocoapp.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image // Importante para las imágenes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as lazyGridItems
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale // Importante para escalar imágenes
import androidx.compose.ui.res.painterResource // Importante para cargar imágenes drawable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.para_ti.chocoapp.R // Necesario para R.drawable.*
import com.para_ti.chocoapp.ui.theme.ChocolateBrown
import com.para_ti.chocoapp.ui.theme.Cream
import com.para_ti.chocoapp.ui.theme.DarkBackground
import com.para_ti.chocoapp.ui.theme.Parati_chocolate_appTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    // Aquí podrías pasar callbacks de navegación o un ViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedBottomNavItem by remember { mutableStateOf(BottomNavRoute.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedRoute = selectedBottomNavItem,
                onItemSelected = { selectedBottomNavItem = it }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Chocolates Para Ti",
                color = Cream,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar chocolates...", color = Color.Gray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Cream.copy(alpha = 0.9f),
                    unfocusedContainerColor = Cream.copy(alpha = 0.8f),
                    disabledContainerColor = Cream.copy(alpha = 0.5f),
                    cursorColor = ChocolateBrown,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = ChocolateBrown,
                    unfocusedTextColor = ChocolateBrown
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            PromotionalBanner() // Este no tiene emoji, así que se queda igual

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Categorías",
                color = Cream,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            CategoriesSection()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Populares",
                color = Cream,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            PopularProductsSection()
        }
    }
}

@Composable
fun PromotionalBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ChocolateBrown.copy(alpha = 0.7f)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Asumiendo que el texto del banner es intencional y no un emoji placeholder
        Text(
            text = "¡30% de descuento en cajas de regalo!", // Si este emoji también era un placeholder para una imagen, cámbialo.
            modifier = Modifier.padding(16.dp),
            color = Cream,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun CategoriesSection() {
    // En una app real, estos datos vendrían de un ViewModel y podrían incluir el resource ID de la imagen
    val categories = listOf(
        Category("Tabletas", R.drawable.logo_para_ti), // Reemplaza con tus IDs de imagen
        Category("Bombones", R.drawable.logo_para_ti),
        Category("Trufas", R.drawable.logo_para_ti),
        Category("Cajas de Regalo", R.drawable.logo_para_ti),
        Category("Especiales", R.drawable.logo_para_ti)
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category = category)
        }
    }
}

// Data class para Categoría (incluye resource de imagen)
data class Category(val name: String, val imageRes: Int) // imageRes sería @DrawableRes

@Composable
fun CategoryItem(category: Category) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Cream.copy(alpha = 0.2f)), // Un fondo sutil si la imagen no llena el círculo
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.name, // Descripción para accesibilidad
                contentScale = ContentScale.Crop, // O ContentScale.Fit, según necesites
                modifier = Modifier.fillMaxSize() // La imagen llena el Box circular
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = category.name,
            color = Cream,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PopularProductsSection() {
    val popularProducts = listOf(
        Product("Chocolate Amargo 70%", "Bs. 35", R.drawable.logo_para_ti), // Reemplaza con tus IDs
        Product("Caja Premium Surtida", "Bs. 120", R.drawable.logo_para_ti),
        Product("Trufas de Avellana", "Bs. 50", R.drawable.logo_para_ti),
        Product("Bombones Artesanales", "Bs. 75", R.drawable.logo_para_ti)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        lazyGridItems(popularProducts) { product ->
            ProductCard(product = product)
        }
    }
}

// Data class para Producto (ya la tenías, asegúrate que imageRes es @DrawableRes)
data class Product(val name: String, val price: String, @DrawableRes val imageRes: Int)

@Composable
fun ProductCard(product: Product) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                contentScale = ContentScale.Crop, // O Fit, según el aspecto de tus imágenes
                modifier = Modifier
                    .size(100.dp) // Tamaño para la imagen del producto
                    .clip(RoundedCornerShape(8.dp)) // Opcional: redondear esquinas de la imagen
                    .background(ChocolateBrown.copy(alpha = 0.05f)) // Fondo muy sutil si la imagen tiene transparencias
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                fontWeight = FontWeight.SemiBold,
                color = ChocolateBrown,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.height(40.dp) // Para asegurar altura uniforme
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.price,
                color = ChocolateBrown.copy(alpha = 0.8f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Enum para BottomNavRoute (sin cambios)
enum class BottomNavRoute(val route: String, val icon: ImageVector, val label: String) {
    Home("home", Icons.Filled.Home, "Inicio"),
    Favorites("favorites", Icons.Filled.Favorite, "Favoritos"),
    Cart("cart", Icons.Filled.ShoppingCart, "Carrito"),
    Profile("profile", Icons.Filled.Person, "Perfil")
}

// BottomNavigationBar (sin cambios)
@Composable
fun BottomNavigationBar(selectedRoute: BottomNavRoute, onItemSelected: (BottomNavRoute) -> Unit) {
    NavigationBar(
        containerColor = ChocolateBrown,
        contentColor = Cream
    ) {
        val navItems = listOf(BottomNavRoute.Home, BottomNavRoute.Favorites, BottomNavRoute.Cart, BottomNavRoute.Profile)
        navItems.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item,
                onClick = { onItemSelected(item) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Cream,
                    unselectedIconColor = Cream.copy(alpha = 0.6f),
                    selectedTextColor = Cream,
                    unselectedTextColor = Cream.copy(alpha = 0.6f),
                    indicatorColor = Cream.copy(alpha = 0.2f)
                )
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun HomeScreenPreview() {
    Parati_chocolate_appTheme(darkTheme = true) {
        HomeScreen()
    }
}

/*
 Ejemplo de cómo podrías tener un drawable placeholder (e.g., res/drawable/ic_category_placeholder.xml):
 <vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
  <path
      android:fillColor="#CCCCCC" // Un color gris para el placeholder
      android:pathData="M21,19V5c0,-1.1 -0.9,-2 -2,-2H5c-1.1,0 -2,0.9 -2,2v14c0,1.1 0.9,2 2,2h14c1.1,0 2,-0.9 2,-2zM8.5,13.5l2.5,3.01L14.5,12l4.5,6H5l3.5,-4.5z"/>
</vector>
 */

