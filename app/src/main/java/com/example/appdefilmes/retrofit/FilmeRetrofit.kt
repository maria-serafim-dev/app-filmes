package com.example.appdefilmes.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Level


class FilmeRetrofit {

    companion object{

        fun getRetrofitInstance(path: String): Retrofit{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()


            return Retrofit.Builder()
                .baseUrl(path)
                .client(client)
                .addConverterFactory((GsonConverterFactory.create()))
                .build()
        }

    }

}