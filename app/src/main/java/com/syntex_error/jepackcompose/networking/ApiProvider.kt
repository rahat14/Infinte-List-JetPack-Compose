package com.syntex_error.jepackcompose.networking

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object ApiProvider {


    private fun retrofitService(): Retrofit {
        val moshi = Moshi.Builder()
            .build()

        return Retrofit
            .Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(ApiService.ROOT_URL)
            .build()
    }


//    private fun getHttpClient(): OkHttpClient {
//        val logging = HttpLoggingInterceptor() // loging the respose for testing purpose only
//        logging.level = HttpLoggingInterceptor.Level.BODY
//        val httpClient = OkHttpClient.Builder()
//        httpClient.callTimeout(2, TimeUnit.MINUTES)
//        httpClient.readTimeout(2, TimeUnit.MINUTES)
//        httpClient.writeTimeout(2, TimeUnit.MINUTES)
//
//        //   httpClient.addInterceptor(logging)
//        return httpClient.build()
//    }

    val dataApi: ApiService by lazy {
        retrofitService().create(ApiService::class.java)
    }
}