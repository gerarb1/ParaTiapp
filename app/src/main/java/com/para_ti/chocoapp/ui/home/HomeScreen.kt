package com.para_ti.chocoapp.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.para_ti.chocoapp.R
import com.para_ti.chocoapp.domain.viewmodel.CartItem
import com.para_ti.chocoapp.domain.viewmodel.ProductViewModel
import com.para_ti.chocoapp.domain.viewmodel.CartViewModel
import kotlinx.coroutines.tasks.await
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.para_ti.chocoapp.data.firebase.Product
import com.para_ti.chocoapp.ui.theme.*
import androidx.compose.material3.ripple
// ------------------------------------------------------
// ENUM DE NAVEGACIÓN INFERIOR
// ------------------------------------------------------
enum class BottomNavRoute(val route: String, val icon: ImageVector, val label: String) {
    Home("home", Icons.Filled.Home, "Inicio"),
    Cart("cart", Icons.Filled.ShoppingCart, "Carrito"),
    Profile("profile", Icons.Filled.Person, "Perfil")
}

// ------------------------------------------------------
// BARRA DE NAVEGACIÓN INFERIOR
// ------------------------------------------------------
@Composable
fun BottomNavigationBar(selectedRoute: BottomNavRoute, onItemSelected: (BottomNavRoute) -> Unit) {
    NavigationBar(containerColor = ChocolateBrown, contentColor = Cream) {
        val navItems = listOf(BottomNavRoute.Home, BottomNavRoute.Cart, BottomNavRoute.Profile)
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

// ------------------------------------------------------
// BANNER PROMOCIONAL
// ------------------------------------------------------
@Composable
fun PromotionalBanner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.logo_para_ti),
                contentDescription = "Banner promocional",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = "¡Ofertas de Temporada!",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

// ------------------------------------------------------
// CHIP DE CATEGORÍA
// ------------------------------------------------------
@Composable
fun CategoryChip(text: String, isSelected: Boolean, onSelected: () -> Unit) {
    Button(
        onClick = onSelected,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Cream else ChocolateBrown,
            contentColor = if (isSelected) ChocolateBrown else Cream
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}

// ------------------------------------------------------
// SECCIÓN DE CATEGORÍAS
// ------------------------------------------------------
@Composable
fun CategoriesSection(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf(
        "Chocolate en bolsa",
        "Chocolate en caja artesanal",
        "Chocolate en envase de regalo",
        "Grageas"
    )
    var selectedCategory by remember { mutableStateOf(categories.firstOrNull()) }

    Column {
        Text("Categorías", color = Cream, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(categories, key = { it }) { category ->
                CategoryChip(
                    text = category,
                    isSelected = category == selectedCategory,
                    onSelected = {
                        selectedCategory = category
                        onCategorySelected(category) //  Notificamos al HomeScreen
                    }
                )
            }
        }
    }
}


// ------------------------------------------------------
// DIALOGO PARA AGREGAR AL CARRITO
// ------------------------------------------------------
@Composable
fun AddToCartDialog(product: Product, onDismiss: () -> Unit, onConfirm: (Int) -> Unit) {
    var cantidad by remember { mutableStateOf(1) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { Button(onClick = { onConfirm(cantidad) }) { Text("Agregar") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
        title = { Text("Agregar al carrito") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(product.nombre)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { if (cantidad > 1) cantidad-- }) { Text("-", fontSize = 20.sp) }
                    Text("$cantidad", fontSize = 18.sp, modifier = Modifier.padding(horizontal = 8.dp))
                    IconButton(onClick = { cantidad++ }) { Text("+", fontSize = 20.sp) }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Precio total: $${"%.2f".format(product.precio * cantidad)}")
            }
        }
    )
}

// ------------------------------------------------------
// CARD DE PRODUCTO DESDE FIREBASE
// ------------------------------------------------------
@Composable
fun ProductCardFirebase(product: Product, cartViewModel: CartViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            //  CORREGIDO: ahora usa la nueva API compatible
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true)
            ) {
                showDialog = true
            },
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberAsyncImagePainter(product.imagenUrl),
                contentDescription = product.nombre,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(product.nombre, fontWeight = FontWeight.Bold, color = ChocolateBrown)
            Text("$${product.precio}", color = ChocolateBrown)
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = ChocolateBrown, contentColor = Cream)
            ) { Text("Agregar al carrito") }
        }
    }

    if (showDialog) {
        AddToCartDialog(
            product = product,
            onDismiss = { showDialog = false },
            onConfirm = { cantidad ->
                cartViewModel.addItem(
                    CartItem(
                        id = product.id,
                        nombre = product.nombre,
                        imagenUrl = product.imagenUrl,
                        precio = product.precio,
                        cantidad = cantidad
                    )
                )
                showDialog = false
            }
        )
    }
}

// ------------------------------------------------------
// PERFIL DE USUARIO
// ------------------------------------------------------
@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://cdn-icons-png.flaticon.com/512/149/149071.png",
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(60.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Usuario", color = Cream, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(containerColor = ChocolateBrown)) {
            Text("Cerrar sesión", color = Cream)
        }
    }
}

// ------------------------------------------------------
// CARRITO VACÍO
// ------------------------------------------------------
@Composable
fun CartScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentAlignment = Alignment.Center
    ) {
        Text("Tu carrito está vacío", color = Cream, fontSize = 18.sp)
    }
}

// ------------------------------------------------------
// HOME: CONTENIDO PRINCIPAL
// ------------------------------------------------------
@Composable
fun HomeContent(
    innerPadding: PaddingValues,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    cartViewModel: CartViewModel
) {
    val productos = remember { mutableStateListOf<Product>() }
    val db = FirebaseFirestore.getInstance()

    // Cargar productos desde Firebase una sola vez
    LaunchedEffect(Unit) {
        val snapshot = db.collection("productos").get().await()
        val fetched = snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
        productos.clear()
        productos.addAll(fetched)
    }

    // 🔹 Filtrado dinámico
    val productosFiltrados = productos.filter { product ->
        val matchesSearch = product.nombre.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "Todos" ||
                product.categoria.equals(selectedCategory, ignoreCase = true)
        matchesSearch && matchesCategory
    }

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
            onValueChange = onSearchChange,
            placeholder = { Text("Buscar chocolates...", color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Cream.copy(alpha = 0.9f),
                unfocusedContainerColor = Cream.copy(alpha = 0.8f),
                cursorColor = ChocolateBrown,
                focusedTextColor = ChocolateBrown,
                unfocusedTextColor = ChocolateBrown
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))
        PromotionalBanner()
        Spacer(modifier = Modifier.height(16.dp))
        CategoriesSection(
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Productos", color = Cream, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxHeight(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productosFiltrados) { product ->
                ProductCardFirebase(product, cartViewModel)
            }
        }
    }
}

// ------------------------------------------------------
// HOME SCREEN
// ------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    cartViewModel: CartViewModel = viewModel(),
    onNavigateToCart: () -> Unit,
    onLogout: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedBottomNavItem by remember { mutableStateOf(BottomNavRoute.Home) }
    var selectedCategory by remember { mutableStateOf("Todos") } // 🔹 Nueva variable de categoría seleccionada

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedRoute = selectedBottomNavItem,
                onItemSelected = { selectedItem ->
                    selectedBottomNavItem = selectedItem
                    if (selectedItem == BottomNavRoute.Cart) {
                        onNavigateToCart()
                    }
                }
            )
        }
    ) { innerPadding ->
        when (selectedBottomNavItem) {
            BottomNavRoute.Home -> HomeContent(
                innerPadding = innerPadding,
                searchQuery = searchQuery,
                onSearchChange = { searchQuery = it },
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                cartViewModel = cartViewModel
            )
            BottomNavRoute.Profile -> ProfileScreen(onLogout = onLogout)
            BottomNavRoute.Cart -> CartScreen()
        }
    }
}

