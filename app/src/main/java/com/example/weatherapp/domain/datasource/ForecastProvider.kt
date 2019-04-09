package com.example.weatherapp.domain.datasource

import com.example.weatherapp.data.db.ForecastDb
import com.example.weatherapp.data.server.ForecastServer
import com.example.weatherapp.domain.model.Forecast
import com.example.weatherapp.domain.model.ForecastList
import com.example.weatherapp.extensions.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> =
        ForecastProvider.SOURCES) {

    companion object {
        const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if(res != null && res.size >= days) res else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources {
        it.requestDayForecast(id)
    }

    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long):
            ForecastList? {
        val res = source.requestForecastByZipCode(zipCode, todayTimeSpan())
        return if(res != null && res.size >= days) res else null
    }

    private fun todayTimeSpan() = System.currentTimeMillis()/ DAY_IN_MILLIS * DAY_IN_MILLIS

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T
        = sources.firstResult { f(it) }
}