package com.example.recipeapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.ui.theme.RecipeAppTheme

@Composable
fun RecipeListScreen() {
    val recipeRepository = remember { RecipeRepository() }
    val factory = RecipeViewModelFactory(recipeRepository)
    val recipeViewModel: RecipeViewModel = viewModel(factory = factory)

    val recipes = recipeViewModel.recipes // This will automatically update when Firestore data changes
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // New Recipe Form
        TextField(value = title, onValueChange = { title = it }, label = { Text("Recipe Title") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Recipe Description") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (title.isNotEmpty() && description.isNotEmpty()) {
                recipeViewModel.addRecipe(Recipe(title, description))
                title = ""
                description = ""
            }
        }) {
            Text("Add Recipe")
        }

        // Recipe List
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(recipes) { recipe ->
                RecipeItem(recipe)
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recipe.title)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = recipe.description)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RecipeAppTheme {
        RecipeListScreen()
    }
}


