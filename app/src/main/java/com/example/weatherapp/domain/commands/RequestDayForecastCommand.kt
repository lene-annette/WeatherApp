package com.example.weatherapp.domain.commands

import com.example.weatherapp.domain.datasource.ForecastProvider
import com.example.weatherapp.domain.model.Forecast

class RequestDayForecastCommand(
    val id: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()) :
    Command<Forecast>{
    override fun execute() = forecastProvider.requestForecast(id)
}