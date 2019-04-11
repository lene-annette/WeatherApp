package com.example.weatherapp.domain.commands

import com.example.weatherapp.domain.datasource.ForecastProvider
import com.example.weatherapp.domain.model.ForecastList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestForecastCommand(private val zipCode: Long,
                             private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    companion object {
        const val DAYS = 7
    }

    override suspend fun execute()= withContext(Dispatchers.IO){
        forecastProvider.requestByZipCode(zipCode, DAYS)
    }


}
