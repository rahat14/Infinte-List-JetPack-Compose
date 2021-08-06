package com.syntex_error.jepackcompose.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntex_error.jepackcompose.models.DogModel
import com.syntex_error.jepackcompose.networking.ApiProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class DogListViewModel : ViewModel() {
    private var page: Int = 1
    var isLoading = mutableStateOf(false)
    var isEnd = mutableStateOf(false)
    var isError = mutableStateOf(false)
    var isMsg = mutableStateOf("")
    val dogList: MutableState<List<DogModel>> = mutableStateOf(ArrayList())

    init {

        intialDogs()
    }

    private fun intialDogs() {
        viewModelScope.launch {
            isLoading.value = true
            isError.value = false
            try {
                val call = ApiProvider.dataApi.getDogsBreeds(1, 20)
                if (call.isSuccessful && call.code() == 200) {
                    val dogs = call.body()
                    if (dogs != null) {
                        dogList.value = dogs
                        isLoading.value = false
                    }
                } else {
                    isLoading.value = false
                    isError.value = true
                    isMsg.value = "Error : ${call.code()}"
                }

            } catch (ex: Exception) {
                isLoading.value = false
                Log.d("TAG", "getNews: ${ex.localizedMessage}")
                isError.value = true
                isMsg.value = "Error : ${ex.localizedMessage}"
            }

        }
    }

    fun getMoreDogs() {

        viewModelScope.launch {
            isError.value = false
            isLoading.value = true
            delay(1000)
            val call = ApiProvider.dataApi.getDogsBreeds(page + 1, 20)
            if (call.isSuccessful && call.code() == 200) {
                val dogs = call.body()
                if (dogs != null) {
                    // this api send empty list when there is no dogs
                    // if
                    if (dogs.isEmpty()) {
                        // its the end
                        isEnd.value = true
                        isLoading.value = false
                        isMsg.value = "You Are At The Last Page !!"
                    } else {
                        addNewDogsToList(dogs)
                    }

                }
            } else {
                isLoading.value = false
                isError.value = true
                isMsg.value = "Error : ${call.code()}"
            }


        }
    }

    private fun addNewDogsToList(dogs: MutableList<DogModel>) {
        val currentList = ArrayList(dogList.value)
        currentList.addAll(dogs)
        dogList.value = currentList
        page += 1
        isLoading.value = false
    }

}