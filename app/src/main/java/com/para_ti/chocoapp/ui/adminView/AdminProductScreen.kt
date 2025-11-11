package com.para_ti.chocoapp.ui.adminView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.para_ti.chocoapp.data.firebase.Product
import com.para_ti.chocoapp.domain.viewmodel.AdminViewModel
import com.para_ti.chocoapp.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    adminViewModel: AdminViewModel = viewModel(),
    onBack: () -> Unit = {}
) {
    val products by adminViewModel.products.collectAsState()
    val loading by adminViewModel.loading.collectAsState()
    val error by adminViewModel.error.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel Admin", color = Cream) },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Volver", color = Cream) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }, containerColor = ChocolateBrown) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Cream)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = DarkBackground
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(DarkBackground)
            .padding(16.dp)
        ) {
            when {
                loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = ChocolateBrown)
                }
                error != null -> {
                    Text(text = error ?: "Error desconocido", color = Cream, modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    if (products.isEmpty()) {
                        Text("No hay productos", color = Cream, modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(items = products, key = { it.id }) { p ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = Cream)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            AsyncImage(
                                                model = p.imagenUrl.ifEmpty { null },
                                                contentDescription = p.nombre,
                                                modifier = Modifier.size(64.dp)
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Text(p.nombre, color = ChocolateBrown, style = MaterialTheme.typography.titleMedium)
                                                Text("Bs ${p.precio}", color = ChocolateBrown)
                                                Text(p.categoria, color = ChocolateBrown.copy(alpha = 0.7f))
                                            }
                                        }
                                        IconButton(onClick = {
                                            adminViewModel.deleteProduct(p.id) { ok, msg ->
                                                scope.launch {
                                                    if (ok) snackbarHostState.showSnackbar("Producto eliminado")
                                                    else snackbarHostState.showSnackbar("Error: ${msg ?: "?"}")
                                                }
                                            }
                                        }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddProductDialog(
            onDismiss = { showAddDialog = false },
            onSave = { newProduct ->
                adminViewModel.addProduct(newProduct) { ok, msg ->
                    scope.launch {
                        if (ok) {
                            snackbarHostState.showSnackbar("Producto agregado")
                            showAddDialog = false
                        } else {
                            snackbarHostState.showSnackbar("Error: ${msg ?: "?"}")
                        }
                    }
                }
            }
        )
    }
}
// --- AÑADE ESTE CÓDIGO AL FINAL DEL ARCHIVO ---

@Composable
fun AddProductDialog(
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Producto", color = ChocolateBrown) },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
                OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") })
                OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") })
                OutlinedTextField(value = stock, onValueChange = { stock = it.filter { ch -> ch.isDigit() } }, label = { Text("Stock") })
                OutlinedTextField(value = imagenUrl, onValueChange = { imagenUrl = it }, label = { Text("URL imagen") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val price = precio.toDoubleOrNull() ?: 0.0
                val stk = stock.toIntOrNull() ?: 0
                val product = Product(
                    id = "", // se asigna en ViewModel
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = price,
                    categoria = categoria,
                    imagenUrl = imagenUrl,
                    disponible = true,
                    stock = stk,
                    temporada = ""
                )
                onSave(product)
            }, colors = ButtonDefaults.buttonColors(containerColor = ChocolateBrown)) {
                Text("Guardar", color = Cream)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar", color = ChocolateBrown) }
        }
    )
}
