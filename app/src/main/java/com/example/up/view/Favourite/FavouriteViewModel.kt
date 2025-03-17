package com.example.up.view.Favourite

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.up.domain.Constant
import com.example.up.model.favourite
import com.example.up.model.products
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class FavouriteViewModel: ViewModel(){
    var favourite by mutableStateOf<List<favourite>>(listOf())
    var products by mutableStateOf<List<products>>(listOf())

    fun getFavourite(){
        viewModelScope.launch {
            try {
                favourite = Constant.supabase.from("favourite")
                    .select().decodeList<favourite>()
                Log.d("favourite", favourite.toString())

            } catch (e: Exception) {
                println(e.message.toString())
            }
        }
    }

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

    fun deleteFavourite(id: Int) {
        viewModelScope.launch {
            try {
                Log.d("id", id.toString())
                Constant.supabase.from("favourite").delete {
                    filter {
                        eq("id", id)
                    }
                }
                Log.d("id", id.toString())
                getFavourite()
            } catch (e: Exception) {
                Log.d("delete", e.message.toString())
            }
        }
    }
}