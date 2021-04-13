package kz.step.weathersoft.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_city_add.*
import kz.step.weathersoft.R
import kz.step.weathersoft.data.City
import kz.step.weathersoft.data.database.CityDatabase
import kz.step.weathersoft.data.database.entity.WeatherCityEntity
import kz.step.weathersoft.data.network.Api
import kz.step.weathersoft.data.network.RetrofitCreator
import kz.step.weathersoft.data.network.returnData.CityReturnData
import kz.step.weathersoft.data.network.returnData.WeatherCityReturnData
import kz.step.weathersoft.ui.MainActivity
import kz.step.weathersoft.ui.base.BaseFragment
import kz.step.weathersoft.ui.contracts.CityAddFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException

class CityAddFragment(var cityFragment: CityFragment, var mainActivity: MainActivity): BaseFragment(), CityAddFragmentContract.View {

    var imageViewBack: ImageView? = null
    var editTextCity: EditText? = null
    var buttonComplete: Button? = null


    val api: Api = RetrofitCreator().initializeApiInterface()
    var apiWeather: Api = RetrofitCreator().initializeApiInterface()

    var cityDatabase: CityDatabase? = null

    override fun initializeLayout(): Int {
        return R.layout.fragment_city_add
    }
    override fun initializeViews() {
        imageViewBack = imageView_fragment_city_add_back
        editTextCity = editText_fragment_city_add_city
        buttonComplete = button_fragment_city_add_complete

    }
    override fun initializeListeners() {
        imageViewBack?.setOnClickListener{
            mainActivity.initiateDisplayFragment(cityFragment)
        }

        buttonComplete?.setOnClickListener {
            getCurrentWeatherApi()
        }
    }
    fun getCurrentWeatherApi(){
        api.initiateGetData(editTextCity?.text.toString(), "aa3c0909c0bce289d19a19886315404e").enqueue(object:
            Callback<CityReturnData> {

            override fun onResponse(call: Call<CityReturnData>, response: Response<CityReturnData>) {


                var city = City()
                if (response.body() != null){
                    city = City().apply {
                        title = response.body()?.nameCity
                        country = response.body()?.sys?.country
                        temp = response.body()?.main?.temp!! - 273.15f
                        weather = response.body()?.weather?.get(0)?.main
                        iconUrl = response.body()?.weather?.get(0)?.icon

                        windSpeed = response.body()?.wind?.speed!!
                        tempMin = response.body()?.main?.tempMin!! - 273.15f
                        tempMax = response.body()?.main?.tempMax!! - 273.15f
                        tempFeelsLike = response.body()?.main?.tempFeelsLike!! - 273.15f

                        humidity = response.body()?.main?.humidity!!

                        lon = response.body()?.coord?.lon!!
                        lat = response.body()?.coord?.lat!!

                    }

                    apiWeather.initiateGetWeather(city.lat, city.lon, apikey = "aa3c0909c0bce289d19a19886315404e").enqueue(object : Callback<WeatherCityReturnData>{
                        override fun onFailure(call: Call<WeatherCityReturnData>, t: Throwable) {
                            var toast: Toast = Toast.makeText(
                                    context,
                                    "Error!",
                                    Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }

                        override fun onResponse(call: Call<WeatherCityReturnData>, response: Response<WeatherCityReturnData>) {
                            if (response.body() != null){

                                var isAdd: Boolean = cityDatabase?.getWeatherCityDao()?.initiateGetWeatherCitiesByName(city.title.toString())!!.size == 0

                                for (item in response.body()?.daily!!){
                                    var tempWeather = WeatherCityEntity().apply {
                                        this.city = city.title
                                        day = item.temp?.day!! - 273.15f
                                        night = item.temp?.night!! - 273.15f
                                        icon = item.weather?.get(0)?.icon
                                    }
                                    if (isAdd){
                                        cityDatabase?.getWeatherCityDao()?.initiateInsertWeatherCity(tempWeather)
                                    }

                                }


                            }
                        }

                    })


                    cityFragment.addCity(city)
                    mainActivity.initiateDisplayFragment(cityFragment)
                }




                else{
                    var toast: Toast = Toast.makeText(context, "This city is not on the list :(", Toast.LENGTH_SHORT)
                    toast.show()
                }

            }
            override fun onFailure(call: Call<CityReturnData>, t: Throwable) {
                var toast: Toast = Toast.makeText(context, "Server request error", Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityDatabase = Room
                .databaseBuilder(requireContext(), CityDatabase::class.java, "cityDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        initializeViews()
        initializeListeners()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return rootView
    }

}