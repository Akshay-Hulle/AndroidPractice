package com.example.composeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeproject.ui.theme.ComposeprojectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeprojectTheme {
                val vm: MainViewModel = viewModel()
                GreetingScreen(vm)
                }
        }
    }
}


@Composable
fun GreetingScreen(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Hello ${viewModel.name.value}!",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(

            value = viewModel.name.value,
            onValueChange = viewModel::onNameChange,
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { viewModel.increaseCounter() }) {
                Text("Clicked ${viewModel.count.value} times")
            }

            Button(onClick = { viewModel.addNameToList() }) {
                Text("Add to List")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Added Names:", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(viewModel.nameList) { item ->
                Text(text = item, modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val tempVM = viewModel<MainViewModel>()
    GreetingScreen(tempVM)
}
