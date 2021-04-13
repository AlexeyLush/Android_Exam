package kz.step.weathersoft.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CityEntity {
    @PrimaryKey
    var title: String = ""

    var country: String? = ""

    var temp: Float = 0f

    var weather: String? = ""

    var iconUrl: String? = ""


    var windSpeed: Float = 0f
    var tempMin: Float = 0f
    var tempMax: Float = 0f
    var tempFeelsLike: Float = 0f
    var humidity: Int = 0

    var lon: Float = 0.0f
    var lat: Float = 0.0f

}