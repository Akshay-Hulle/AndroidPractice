package com.supremology.fakestore.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.supremology.fakestore.viewmodel.ProductViewModel
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductScreen(viewModel: ProductViewModel = viewModel()) {
    val products by viewModel.products.observeAsState(emptyList())
    val loading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()

    val refreshState = rememberPullRefreshState(loading, { viewModel.loadProducts() })

    LaunchedEffect(true) {
        viewModel.loadProducts()
    }

    Box(Modifier.fillMaxSize().pullRefresh(refreshState)) {
        if (loading && products.isEmpty()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else if (error != null) {
            Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(error!!)
                Button(onClick = { viewModel.loadProducts() }) { Text("Retry") }
            }
        } else {
            LazyColumn {
                items(products) {
                    ProductItem(product = it)
                }
            }
        }

        PullRefreshIndicator(loading, refreshState, Modifier.align(Alignment.TopCenter))
    }
}
