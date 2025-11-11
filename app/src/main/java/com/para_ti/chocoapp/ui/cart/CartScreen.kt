package com.para_ti.chocoapp.ui.cart

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.para_ti.chocoapp.domain.viewmodel.CartItem
import com.para_ti.chocoapp.domain.viewmodel.CartViewModel
import com.para_ti.chocoapp.ui.theme.Cream
import com.para_ti.chocoapp.ui.theme.ChocolateBrown

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = viewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    LaunchedEffect(Unit) {
        cartViewModel.loadCart()
    }

    val total = cartItems.sumOf { it.precio * it.cantidad }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Mi Carrito",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = ChocolateBrown
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (cartItems.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Tu carrito está vacío ", color = ChocolateBrown)
            }
        } else {
            LazyColumn {
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onRemove = { cartViewModel.removeItem(item.id) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Total: $${"%.2f".format(total)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            val context = LocalContext.current

            Button(
                onClick = {
                    if (cartItems.isNotEmpty()) {
                        cartViewModel.clearCart()
                        Toast.makeText(context, "Compra realizada con éxito ", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Tu carrito está vacío", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ChocolateBrown,
                    contentColor = Cream
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Finalizar compra")
            }

        }
    }
}

@Composable
fun CartItemCard(item: CartItem, onRemove: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Cream)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.imagenUrl),
                contentDescription = item.nombre,
                modifier = Modifier.size(70.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.nombre, fontWeight = FontWeight.Bold)
                Text("Cantidad: ${item.cantidad}")
                Text("Precio: $${item.precio}")
            }

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar"
                )
            }
        }
    }
}
