package com.example.weatherapp.data.db

import com.example.weatherapp.domain.datasource.ForecastDataSource
import com.example.weatherapp.domain.model.Forecast
import com.example.weatherapp.domain.model.ForecastList
import com.example.weatherapp.extensions.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select


class ForecastDb(private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 private val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
            .whereSimple(dailyRequest, zipCode.toString(), date.toString())
            .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
            .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
            .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        // instead of: if (city != null) dataMapper.convertToDomain(city) else null
        city?.let {dataMapper.convertToDomain(it)}
    }

    override fun requestDayForecast(id: Long): Forecast? = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME).byId(id)
            .parseOpt { DayForecast(HashMap(it))}

        //instead of: if(forecast != null) dataMapper.convertDayToDomain(forecast) else null
        forecast?.let {dataMapper.convertDayToDomain(it)}
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {

        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}