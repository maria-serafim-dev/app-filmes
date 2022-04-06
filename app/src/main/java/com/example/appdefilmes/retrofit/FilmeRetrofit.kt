package com.example.appdefilmes.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FilmeRetrofit {

    companion object{

        fun getRetrofitInstance(path: String): Retrofit{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
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