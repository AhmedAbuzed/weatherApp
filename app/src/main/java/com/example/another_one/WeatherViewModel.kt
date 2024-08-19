package com.example.another_one

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData : StateFlow<WeatherResponse?> = _weatherData
    private val weatherApi = WeatherApi.create()

    fun fetchWeather(city:String , apiKey: String ){

        viewModelScope.launch {
            try{
                val response= weatherApi.getWeather(city,apiKey)
                _weatherData.value = response
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }
}