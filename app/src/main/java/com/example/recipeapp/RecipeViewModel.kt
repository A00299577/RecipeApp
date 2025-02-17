package com.example.recipeapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _recipes = mutableStateListOf<Recipe>()
    val recipes: List<Recipe> get() = _recipes

    private var recipeListener: ListenerRegistration? = null

    init {
        fetchRecipes()
    }

    // Fetch recipes from Firestore (one-time fetch)
    private fun fetchRecipes() {
        viewModelScope.launch {
            _recipes.clear()
            _recipes.addAll(repository.getRecipes())
        }
    }

    // Add a recipe to Firestore
    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.addRecipe(recipe)
            fetchRecipes() // Optionally refresh the list after adding a new recipe
        }
    }

    // Cleanup listener when the ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        recipeListener?.remove() // Remove listener to prevent memory leaks
    }
}
