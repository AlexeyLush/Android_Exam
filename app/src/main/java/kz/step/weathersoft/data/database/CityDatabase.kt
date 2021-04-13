package kz.step.weathersoft.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kz.step.weathersoft.data.database.dao.CityDao
import kz.step.weathersoft.data.database.dao.WeatherCityDao
import kz.step.weathersoft.data.database.entity.CityEntity
import kz.step.weathersoft.data.database.entity.WeatherCityEntity

@Database(entities = arrayOf(CityEntity::class, WeatherCityEntity::class), version = 5)
abstract class CityDatabase: RoomDatabase() {

    abstract fun getCityDao(): CityDao
    abstract fun getWeatherCityDao(): WeatherCityDao

    companion object{
        fun initiateGenerateDatabase(context: Context): CityDatabase{
            return Room.databaseBuilder(context, CityDatabase::class.java,"cityDatabase").build()
        }

        fun initiateGetCityDao(context: Context): CityDao{
            return initiateGenerateDatabase(context).getCityDao()
        }
    }

}