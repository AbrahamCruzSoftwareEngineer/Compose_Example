package com.example.compose_example

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyViewModel : ViewModel() {
    private val _images = MutableLiveData<List<String>>()
    val images: LiveData<List<String>> = _images

    fun searchByNameFullResponse(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    getRetrofit().create(APIService::class.java).getImageList("$query/images")
                if (response.isSuccessful) {
                    _images.postValue(response.body()?.images ?: emptyList())
                } else {
                    _images.postValue(listOf(response.errorBody().toString()))
                }
            } catch (e: Exception) {
                Log.d(TAG, "searchByName: ${e.message}")
            }
        }
    }

    fun searchByNameLimited(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getRetrofit().create(APIService::class.java).getImageList("$query/images")
                if (response.isSuccessful) {
                    // Take only the first 5 images from the response
                    val limitedImages = response.body()?.images?.take(5) ?: emptyList()
                    _images.postValue(limitedImages)
                } else {
                    // Handling errors - You might want to handle errors differently
                    // Posting an error message as a list with one item for demonstration
                    _images.postValue(listOf("Error: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                Log.d(TAG, "searchByName: ${e.message}")
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val TAG = "MyViewModel"
    }
}