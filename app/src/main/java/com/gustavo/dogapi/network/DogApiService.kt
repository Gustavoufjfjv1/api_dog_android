package com.gustavo.dogapi.network

import com.gustavo.dogapi.model.DogDetailResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breed/{name}/images/random")
    suspend fun getDog(@Path("name") name: String): DogDetailResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://dog.ceo/api/"

    val apiService: DogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }
}