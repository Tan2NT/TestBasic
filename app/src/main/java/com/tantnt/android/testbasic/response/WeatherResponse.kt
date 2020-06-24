package com.tantnt.android.testbasic.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val count: Int,
    val data: List<Data>
)

data class Data(
    @SerializedName("app_temp")
    val appTemp: Double,
    val aqi: Int,
    @SerializedName("city_name")
    val cityName: String,
    val clouds: Int,
    @SerializedName("country_code")
    val countryCode: String,
    val datetime: String,
    val dewpt: Double,
    val dhi: Double,
    val dni: Double,
    @SerializedName("elev_angle")
    val elevAngle: Double,
    val ghi: Double,
    @SerializedName("h_angle")
    val hAngle: Double,
    @SerializedName("last_ob_time")
    val lastObTime: String,
    val lat: Double,
    val lon: Double,
    @SerializedName("ob_time")
    val obTime: String,
    val pod: String,
    val precip: Int,
    val pres: Double,
    val rh: Int,
    val slp: Int,
    val snow: Int,
    @SerializedName("solar_rad")
    val solarRad: Double,
    @SerializedName("state_code")
    val stateCode: String,

    val weather: Weather,
    @SerializedName("wind_cdir")
    val windCdir: String,
    @SerializedName("wind_cdir_full")
    val windCdirFull: String,
    @SerializedName("wind_dir")
    val windDir: Int,
    @SerializedName("wind_spd")
    val windSpd: Double
)

data class Weather(
    val code: String,
    val description: String,
    val icon: String
)