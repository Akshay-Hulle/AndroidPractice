package com.supremology.fakestore2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.supremology.fakestore2.ui.components.ProductItem
import com.supremology.fakestore2.ui.theme.FakeStore2Theme
import com.supremology.fakestore2.ui.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.supremology.fakestore2.data.model.Product
import com.supremology.fakestore2.ui.components.ProductDetailCard
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.supremology.fakestore2.ui.theme.PurpleGrey40


class MainActivity : ComponentActivity() {
    @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ProductViewModel = viewModel()
            val products by viewModel.products.observeAsState(emptyList())
            val error by viewModel.error.observeAsState()
            var selectedProduct by remember { mutableStateOf<Product?>(null) }

            FakeStore2Theme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Fake Store") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = PurpleGrey40,
                                titleContentColor = Color.White
                            )
                        )
                    },
                    content = { paddingValues ->
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize()
                                .background(Color(0xFFF3E5F5)) // Light Purple
                        )
                        if (error != null) {
                            Text(
                                text = "error",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(paddingValues).padding(16.dp)
                            )
                        } else {
                            LazyColumn(contentPadding = paddingValues) {
                                items(products) { product ->
                                    ProductItem(product,
                                            onClick = {selectedProduct = product })
                                }
                            }
                            selectedProduct?.let { product ->
                                androidx.compose.ui.window.Dialog(onDismissRequest = {
                                    selectedProduct = null
                                }) {
                                    ProductDetailCard(product = product, onClose = {
                                        selectedProduct = null
                                    })
                                }
                            }
                        }
                    },
                    bottomBar = {
                        BottomAppBar(
                            containerColor = PurpleGrey40,
                            contentColor = Color.White
                        ) {
                            Text(
                                text = "The best things in life are purchased",
                                modifier = Modifier.padding(start = 8.dp)
                                    .fillMaxWidth(),
                                color = Color.White
                            )
                        }
                    }

                )
            }
        }
    }
}
