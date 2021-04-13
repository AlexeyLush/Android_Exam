package kz.step.weathersoft.data.database.dao

import androidx.room.*
import kz.step.weathersoft.data.City
import kz.step.weathersoft.data.database.entity.CityEntity

@Dao
interface CityDao {

    @Insert
    fun initiateInsertCity(city: CityEntity)

    @Insert
    fun initiateInsertCities(cities: List<CityEntity>)

    @Delete
    fun initiateDeleteCity(city: CityEntity)

    @Query("SELECT * FROM cityentity")
    fun initiateGetCities(): List<CityEntity>

    @Query("SELECT * FROM cityentity WHERE title = :title")
    fun initiateGetCity(title: String): CityEntity

    @Update
    fun initiateUpdateCity(city: CityEntity)

}