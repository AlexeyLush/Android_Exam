package kz.step.weathersoft.data.network.returnData

import com.google.gson.annotations.SerializedName

class WeatherCityReturnData {

    var city: String? = null

    @SerializedName("daily")
    var daily: List<Day>? = null

    class Day{
        @SerializedName("temp")
        var temp: Temp? = null

        class Temp{
            @SerializedName("day")
            var day: Float? = null
            @SerializedName("night")
            var night: Float? = null
        }

        @SerializedName("weather")
        var weather: List<Weather>? = null
        class Weather{
            @SerializedName("icon")
            var icon: String? = null
        }
    }

}