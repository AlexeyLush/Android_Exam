package kz.step.weathersoft.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(
        entity = CityEntity::class,
        parentColumns = arrayOf("title"),
        childColumns = arrayOf("city"))))
class WeatherCityEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var city: String? = null

    var day: Float? = null
    var night: Float? = null


    var icon: String? = null


}