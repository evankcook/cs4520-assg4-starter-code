package com.cs4520.assignment4.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cs4520.assignment4.ProductViewModel
import com.cs4520.assignment4.R
import com.cs4520.assignment4.model.Product


@Composable
fun ProductItem(product: Product) {

    val backgroundColor = when (product.type) {
        "Equipment" -> Color(0xFFE06666)
        "Food" -> Color(0xFFFFD965)
        else -> Color(0xFFFFFFFF) // White when type error
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = when (product.type) {
                "Equipment" -> R.drawable.equipment
                "Food" -> R.drawable.food
                else -> R.drawable.food
            }),
            contentDescription = "Product Image",
            modifier = Modifier
                .size(height = 100.dp, width = 100.dp)
                .aspectRatio(1f)
                .padding(16.dp),
            contentScale = ContentScale.Crop

        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {

            Text(text = product.name, color = Color.Black, modifier = Modifier.padding(vertical = 5.dp))
            if (!product.expiryDate.isNullOrEmpty()) {
                Text(text = product.expiryDate, color = Color.Black, modifier = Modifier.padding(vertical = 5.dp))
            }
            Text(text = "$${String.format("%.2f", product.price)}", color = Color.Black,
                modifier = Modifier.padding(vertical = 5.dp))

        }
    }
}

@Composable
fun ProductList(productList: List<Product>) {
    LazyColumn {
        items(productList) { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
fun ProductListScreen(navController: NavController, productViewModel: ProductViewModel = viewModel()) {
    LaunchedEffect(true) {
        productViewModel.getProducts()
    }
    val isLoading = productViewModel.isLoading.observeAsState().value
    val productList = productViewModel.products.observeAsState(listOf()).value

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isLoading == true) {
            CircularProgressIndicator()
        } else if (productList.isNotEmpty()) {
            ProductList(productList = productList)
        } else {
            Text(text = "No products available")
        }
    }
}