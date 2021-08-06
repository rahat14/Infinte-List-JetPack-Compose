package com.syntex_error.jepackcompose.networking

import com.syntex_error.jepackcompose.models.DogModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        val ROOT_URL: String = "https://api.thedogapi.com/v1/"
    }

    //get app settings
    @GET("breeds")
   suspend fun getDogsBreeds(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<MutableList<DogModel>>


}