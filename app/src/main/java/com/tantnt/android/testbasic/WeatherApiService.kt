package com.tantnt.android.testbasic

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tantnt.android.testbasic.response.WeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val WEATHER_API_KEY = "c7ce4a9cd7e64318b1a3aa17f42ba918"
const val WEATHER_BASE_URL = "https://api.weatherbit.io/v2.0/"

interface WeatherApiService {
    @GET("current?")
    suspend fun getCurrentWeatherByCityName(
        @Query("city") location: String
    ): WeatherResponse
}


object WeatherApiNetwork {
    // create a request interceptor
    val requestInterceptor = Interceptor {
        val url = it.request()
            .url()
            .newBuilder()
            .addQueryParameter("key", WEATHER_API_KEY)
            .build()

        val request = it.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor it.proceed(request)
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val weatherApi = retrofit.create(WeatherApiService::class.java)
}