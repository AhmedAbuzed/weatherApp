package com.example.another_one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.another_one.ui.theme.Another_oneTheme
import com.example.another_one.ui.theme.BlueJC

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherScreen()
        }
    }
}

@Composable
fun WeatherScreen() {
    val viewModel: WeatherViewModel = viewModel()
    val weatherDa by viewModel.weatherData.collectAsState()

    var city by remember { mutableStateOf("") }
    var searchedCity by remember { mutableStateOf<String?>(null) }
    val apiKey = "89cd184d827286d463326cedef07eb81"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.img),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            searchedCity?.let {
                Text(
                    text = it,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.9f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = BlueJC,
                    focusedLabelColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.fetchWeather(city, apiKey)
                    searchedCity = city
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5).copy(alpha = 0.9f))
            ) {
                Text(text = "Search", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
            weatherDa?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherCard(label = "Wind", value = "${it.main.wind} KM/H")
                    WeatherCard(label = "Temperature", value = "${it.main.temp} °C")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherCard(label = "Humidity", value = "${it.main.humidity}%")
                    WeatherCard(label = "Description", value = it.weather[0].description)
                }
            }
        }
    }
}

@Composable
fun WeatherCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1E88E5)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreview() {
    Another_oneTheme {
        WeatherScreen()
    }
}
