package kz.step.weathersoft.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_city.*
import kotlinx.android.synthetic.main.fragment_weather.*
import kz.step.weathersoft.R
import kz.step.weathersoft.data.City
import kz.step.weathersoft.data.Weather
import kz.step.weathersoft.data.database.CityDatabase
import kz.step.weathersoft.data.network.returnData.WeatherCityReturnData
import kz.step.weathersoft.domain.UseCaseFindIcon
import kz.step.weathersoft.ui.MainActivity
import kz.step.weathersoft.ui.adapters.CitiesAdapter
import kz.step.weathersoft.ui.adapters.WeatherAdapter
import kz.step.weathersoft.ui.base.BaseFragment
import kz.step.weathersoft.ui.contracts.WeatherFragmentContract

class WeatherFragment(var city: City, var cityFragment: CityFragment, var mainActivity: MainActivity): BaseFragment(), WeatherFragmentContract.View {

    var imageViewClose: ImageView? = null
    var imageViewIcon: ImageView? = null

    var textViewCity: TextView? = null

    var textViewTempCurrent: TextView? = null
    var textViewTempMax: TextView? = null
    var textViewTempMin: TextView? = null
    var textViewTempFeelsLike: TextView? = null

    var textViewWind: TextView? = null

    var textViewHumidity: TextView? = null

    var weatherAdapter: WeatherAdapter? = null

    var cityDatabase: CityDatabase? = null

    override fun initializeLayout(): Int {
        return R.layout.fragment_weather
    }


    override fun initializeViews() {
        imageViewClose = imageView_fragment_weather_close
        imageViewIcon = imageView_fragment_weather_icon

        textViewCity = textView_fragment_weather_city

        textViewTempCurrent = textView_fragment_weather_temp_current
        textViewTempMax = textView_fragment_weather_temp_max
        textViewTempMin = textView_fragment_weather_temp_min
        textViewTempFeelsLike = textView_fragment_weather_temp_feels_like

        textViewWind = textView_fragment_weather_wind

        textViewHumidity = textView_fragment_weather_humidity
    }

    fun initializeListeners(){
        imageViewClose?.setOnClickListener {
            mainActivity.initiateDisplayFragment(cityFragment)
            Log.d("CLOSE","CLOSE")
        }
    }

    fun initializeValues(){


        textViewCity?.text = "${city.title}, ${city.country}"

        textViewTempCurrent?.text = "Current: ${city.temp.toInt()} °C"
        textViewTempMax?.text = "Max: ${city.tempMax.toInt()} °C"
        textViewTempMin?.text = "Min: ${city.tempMin.toInt()} °C"
        textViewTempFeelsLike?.text = "Feels like: ${city.tempFeelsLike.toInt()} °C"

        textViewWind?.text = "Speed: ${city.windSpeed} m/s"
        textViewHumidity?.text = "Humidity: ${city.humidity}%"

        imageViewIcon?.setImageResource(UseCaseFindIcon().initiateIcon(city.iconUrl!!))



    }


    fun initializeAdapter(weatherList: MutableList<Weather>) {
        Log.d("GO", "ADAPTER")
        Log.d("GO", weatherList.size.toString())
        weatherAdapter = WeatherAdapter(weatherList)
        recyclerview_fragment_weather.adapter = weatherAdapter
        initializeLinearLayoutManager()
    }


    fun initializeLinearLayoutManager() {
        var temp = LinearLayoutManager(requireContext())
        temp.orientation = LinearLayoutManager.HORIZONTAL
        recyclerview_fragment_weather.layoutManager = temp
        recyclerview_fragment_weather.itemAnimator = DefaultItemAnimator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityDatabase = Room
                .databaseBuilder(requireContext(), CityDatabase::class.java, "cityDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

        var tempWeatherList = cityDatabase?.getWeatherCityDao()!!.initiateGetWeatherCitiesByName(city.title.toString())
//        var tempWeatherList = cityDatabase?.getWeatherCityDao()!!.initiateGetWeatherCities()

        var weatherList = mutableListOf<Weather>()

        for (item in tempWeatherList){
            weatherList.add(Weather().apply {
                this.city = item.city
                this.day = item.day
                this.night = item.night
                this.icon = item.icon
            })
        }

        initializeAdapter(weatherList)
        initializeViews()
        initializeListeners()
        initializeValues()
    }

}