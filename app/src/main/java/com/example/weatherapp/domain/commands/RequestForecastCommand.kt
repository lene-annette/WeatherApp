package com.example.weatherapp.domain.commands

import com.example.weatherapp.domain.datasource.ForecastProvider
import com.example.weatherapp.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: Long,
                             private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    companion object {
        const val DAYS = 7
    }

    override fun execute(): ForecastList =
            forecastProvider.requestByZipCode(zipCode, DAYS)

}
