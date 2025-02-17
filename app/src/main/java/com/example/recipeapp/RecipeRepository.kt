package com.example.recipeapp

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RecipeRepository {

    private val db = FirebaseFirestore.getInstance()
    private val recipesCollection = db.collection("recipes")

    // Get recipes from Firestore
    suspend fun getRecipes(): List<Recipe> {
        return try {
            val snapshot = recipesCollection.get().await()
            snapshot.toObjects(Recipe::class.java)
        } catch (e: Exception) {
            emptyList() // Return empty list on error
        }
    }

    // Add a new recipe to Firestore
    suspend fun addRecipe(recipe: Recipe) {
        try {
            recipesCollection.add(recipe).await()
        } catch (e: Exception) {
            // Handle any error here
        }
    }
}