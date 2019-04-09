package com.example.weatherapp.domain.datasource

import com.example.weatherapp.domain.model.Forecast
import com.example.weatherapp.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
    fun requestDayForecast(id:Long): Forecast?
}