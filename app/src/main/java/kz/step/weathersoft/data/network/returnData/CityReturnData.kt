package kz.step.weathersoft.data.network.returnData

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CityReturnData {
    @SerializedName("sys")
    var sys: Sys? = null
    @SerializedName("main")
    var main: Main? = null
    @SerializedName("weather")
    var weather: List<Weather?>? = null
    @SerializedName("name")
    var nameCity: String? = null
    @SerializedName("wind")
    var wind: Wind? = null
    @SerializedName("coord")
    var coord: Coord? = null


    class Coord{
        @SerializedName("lon")
        var lon: Float = 0.0f
        @SerializedName("lat")
        var lat: Float = 0.0f
    }

    class Wind{
        @SerializedName("speed")
        var speed: Float? = null
    }

    class Main {
        @SerializedName("temp")
        var temp: Float = 0.0f
        @SerializedName("temp_min")
        var tempMin: Float = 0f
        @SerializedName("temp_max")
        var tempMax: Float = 0f
        @SerializedName("feels_like")
        var tempFeelsLike: Float = 0f
        @SerializedName("humidity")
        var humidity: Int = 0
    }

    class Sys {
        @SerializedName("country")
        var country: String? = null
    }

    class Weather{
        @SerializedName("main")
        var main: String? = null
        @SerializedName("icon")
        var icon: String? = null
    }

}

