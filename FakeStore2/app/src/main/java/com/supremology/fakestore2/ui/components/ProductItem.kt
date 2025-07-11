package com.supremology.fakestore2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.supremology.fakestore2.data.model.Product
import com.supremology.fakestore2.ui.theme.PurpleGrey80


@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(modifier = Modifier
        .clickable(onClick = onClick)
        .padding(8.dp)
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PurpleGrey80)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(product.title, style = MaterialTheme.typography.titleMedium)
                Text("₹${(product.price)*100}", style = MaterialTheme.typography.bodyLarge)
                Text("(${product.category})", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
