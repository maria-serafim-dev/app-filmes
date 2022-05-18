package com.example.appdefilmes.retrofit

import com.example.appdefilmes.data.baseUrl
import com.example.appdefilmes.retrofit.service.FilmeService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(client)
    .addConverterFactory((GsonConverterFactory.create()))
    .build()


object FilmeApi{
    val retrofitService : FilmeService by lazy {
        retrofit.create(FilmeService::class.java)
    }
}
