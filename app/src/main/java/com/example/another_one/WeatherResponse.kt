package com.example.another_one

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather:List<Weather>

)

data class Main(

val temp: Float,
    val humidity: Int,
    val wind: Float,

)


data class Weather(
    val description: String,
)
