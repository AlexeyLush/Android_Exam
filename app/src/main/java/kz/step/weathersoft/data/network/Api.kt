package kz.step.weathersoft.data.network

import kz.step.weathersoft.data.network.returnData.CityReturnData
import kz.step.weathersoft.data.network.returnData.WeatherCityReturnData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("data/2.5/weather?")
    fun initiateGetData(@Query("q") q: String, @Query("apikey") apikey: String): Call<CityReturnData>

    @GET("data/2.5/onecall?")
    fun initiateGetWeather(@Query("lat") lat: Float, @Query("lon") lon: Float, @Query("exclude") exclude: String = "current,minutely,hourly,alerts", @Query("apikey") apikey: String): Call<WeatherCityReturnData>


}