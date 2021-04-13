package kz.step.weathersoft.data.database.dao

import androidx.room.*
import kz.step.weathersoft.data.database.entity.CityEntity
import kz.step.weathersoft.data.database.entity.WeatherCityEntity

@Dao
interface WeatherCityDao {
    @Insert
    fun initiateInsertWeatherCity(weatherCityEntity: WeatherCityEntity)

    @Insert
    fun initiateInsertWeatherCities(weatherCities: List<WeatherCityEntity>)

    @Delete
    fun initiateDeleteWeatherCity(weatherCityEntity: WeatherCityEntity)

    @Query("SELECT * FROM weatherCityEntity")
    fun initiateGetWeatherCities(): List<WeatherCityEntity>

    @Query("SELECT * FROM weatherCityEntity WHERE city = :city")
    fun initiateGetWeatherCitiesByName(city: String): MutableList<WeatherCityEntity>

    @Update
    fun initiateUpdateWeatherCity(weatherCityEntity: WeatherCityEntity)

    @Query("DELETE FROM weatherCityEntity WHERE city = :city")
    fun initiateDeleteWeatherCities(city: String)


}