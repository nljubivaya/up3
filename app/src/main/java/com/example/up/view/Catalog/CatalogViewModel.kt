package com.example.up.view.Catalog

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.up.domain.Constant
import com.example.up.model.AddFavourite
import com.example.up.model.categories
import com.example.up.model.products
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class CatalogViewModel : ViewModel(){
    var products by mutableStateOf<List<products>>(listOf())
    var categories by mutableStateOf<List<categories>>(listOf())

    fun getProducts() {
        viewModelScope.launch {
            try {
                products = Constant.supabase.from("products")
                    .select().decodeList<products>()
                Log.d("products", products.toString())
                if (products.isEmpty()) {
                    Log.d("products", "No products found")
                }
            } catch (e: Exception) {
                Log.e("products", e.message.toString())
            }
        }
    }
    fun addFavourite(id: String, onDismissRequest: () -> Unit) {
        viewModelScope.launch {
            try {
                val user = Constant.supabase.auth.currentUserOrNull()
                if (user != null) {
                    Constant.supabase.from("favourite").insert(
                        AddFavourite(
                            product_id = id,
                            user_id = user.id
                        )
                    )
                    onDismissRequest()
                }
            } catch (e: Exception) {
                Log.d("add", e.message.toString())
            }
        }
    }
    fun getCatrgories() {
        viewModelScope.launch {
            try {
                categories = Constant.supabase.from("categories")
                    .select().decodeList<categories>()
                Log.d("categories", categories.toString())
                if (categories.isEmpty()) {
                    Log.d("categories", "No categories found")
                }
            } catch (e: Exception) {
                Log.e("categories", e.message.toString())
            }
        }
    }


}