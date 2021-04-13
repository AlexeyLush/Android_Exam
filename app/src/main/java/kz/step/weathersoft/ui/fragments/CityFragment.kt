package kz.step.weathersoft.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_city.*
import kz.step.weathersoft.R
import kz.step.weathersoft.data.City
import kz.step.weathersoft.data.database.CityDatabase
import kz.step.weathersoft.data.database.entity.CityEntity
import kz.step.weathersoft.data.database.entity.WeatherCityEntity
import kz.step.weathersoft.data.network.Api
import kz.step.weathersoft.data.network.RetrofitCreator
import kz.step.weathersoft.data.network.returnData.CityReturnData
import kz.step.weathersoft.data.network.returnData.WeatherCityReturnData
import kz.step.weathersoft.ui.MainActivity
import kz.step.weathersoft.ui.adapters.CitiesAdapter
import kz.step.weathersoft.ui.base.BaseFragment
import kz.step.weathersoft.ui.contracts.CityFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CityFragment(var mainActivity: MainActivity) : BaseFragment(), CityFragmentContract.View {


    var citiesAdapter: CitiesAdapter? = null
    var cities: MutableList<City> = mutableListOf()

    val api: Api = RetrofitCreator().initializeApiInterface()
    var apiWeather: Api = RetrofitCreator().initializeApiInterface()

    var buttonAddCity: Button? = null

    var cityDatabase: CityDatabase? = null

    override fun initializeLayout(): Int {
        return R.layout.fragment_city
    }
    override fun initializeViews() {
        buttonAddCity = button_fragment_city_add

        buttonAddCity?.setOnClickListener {
            mainActivity.initiateDisplayFragment(CityAddFragment(this, mainActivity))
        }
    }
    fun textViewNoCities() {
        if (cities.size == 0) {
            textview_fragment_city_no_city.visibility = View.VISIBLE
        } else {
            textview_fragment_city_no_city.visibility = View.GONE
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cityDatabase = Room
            .databaseBuilder(requireContext(), CityDatabase::class.java, "cityDatabase")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()


        var tempList: List<CityEntity> = cityDatabase?.getCityDao()!!.initiateGetCities()

        getCurrentWeatherApi(tempList)



    }
    fun getCurrentWeatherApi(tempList: List<CityEntity>){
        if (!isNetworkAvailable(context!!.applicationContext)) {
            for (city in tempList) {
                cities.add(City().apply {
                    title = city.title
                    country = city.country
                    temp = city.temp
                    weather = city.weather
                    iconUrl = city.iconUrl
                    windSpeed = city.windSpeed
                    tempMin = city.tempMin
                    tempMax = city.tempMax
                    tempFeelsLike = city.tempFeelsLike
                    humidity = city.humidity
                    lon = city.lon
                    lat = city.lat
                })
            }

            textViewNoCities()
            initializeLinearLayoutManager()
            initializeAdapter()

        }
        else {
            for (city in tempList) {
                api.initiateGetData(
                    city.title,
                    "aa3c0909c0bce289d19a19886315404e"
                ).enqueue(object :
                    Callback<CityReturnData> {

                    override fun onResponse(
                        call: Call<CityReturnData>,
                        response: Response<CityReturnData>
                    ) {


                        if (response.body() != null) {

                            cities.add(City().apply {
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
                            })


                            var tempCityEntity = CityEntity().apply {
                                title = response.body()?.nameCity.toString()
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

                            if (cityDatabase?.getCityDao()?.initiateGetCity(response.body()?.nameCity.toString()) != null){
                                cityDatabase?.getCityDao()?.initiateUpdateCity(tempCityEntity)
                            }else{
                                cityDatabase?.getCityDao()?.initiateInsertCity(tempCityEntity)
                            }


                            apiWeather.initiateGetWeather(tempCityEntity.lat, tempCityEntity.lon, apikey = "aa3c0909c0bce289d19a19886315404e").enqueue(object : Callback<WeatherCityReturnData>{
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
                                        Log.d("Count", cityDatabase?.getWeatherCityDao()?.initiateGetWeatherCitiesByName(tempCityEntity.title)?.size.toString())
                                        cityDatabase?.getWeatherCityDao()?.initiateDeleteWeatherCities(tempCityEntity.title)
                                        Log.d("Count", cityDatabase?.getWeatherCityDao()?.initiateGetWeatherCitiesByName(tempCityEntity.title)?.size.toString())
                                        for (item in response.body()?.daily!!){
                                            var tempWeather = WeatherCityEntity().apply {
                                                this.city = tempCityEntity.title
                                                day = item.temp?.day!! - 273.15f
                                                night = item.temp?.night!! - 273.15f
                                                icon = item.weather?.get(0)?.icon
                                            }

                                            cityDatabase?.getWeatherCityDao()?.initiateInsertWeatherCity(tempWeather)
                                        }

                                        textViewNoCities()
                                        initializeLinearLayoutManager()
                                        initializeAdapter()

                                    }
                                }

                            })
                        } else {
                            var toast: Toast = Toast.makeText(
                                context,
                                "This city is not on the list :(",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }

                    }

                    override fun onFailure(call: Call<CityReturnData>, t: Throwable) {
                        var toast: Toast = Toast.makeText(
                            context,
                            "Server request error",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                })
            }

        }

        initializeViews()

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return rootView
    }
    fun isNetworkAvailable(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    override fun initializeAdapter() {
        citiesAdapter = CitiesAdapter(citiesList = this.cities, cityFragment = this,
            mainActivity = mainActivity
        )
        recyclerview_fragment_city.adapter = citiesAdapter
        initializeLinearLayoutManager()
    }
    override fun initializeLinearLayoutManager() {
        recyclerview_fragment_city.layoutManager = LinearLayoutManager(requireContext())
    }
    fun removeCity(position: Int) {
        var city = cities.removeAt(position)

        var temp: CityEntity = CityEntity().apply {
            title = city.title.toString()
            country = city.country
            temp = city.temp
            weather = city.weather
            iconUrl = city.iconUrl
            windSpeed = city.windSpeed
            tempMin = city.tempMin
            tempMax = city.tempMax
            tempFeelsLike = city.tempFeelsLike
            humidity = city.humidity
        }
        cityDatabase?.getWeatherCityDao()?.initiateDeleteWeatherCities(temp.title)
        cityDatabase?.getCityDao()?.initiateDeleteCity(temp)

        textViewNoCities()
        initializeAdapter()
        initializeLinearLayoutManager()
    }
    fun addCity(city: City) {

        var temp: CityEntity = CityEntity().apply {
            title = city.title.toString()
            country = city.country
            temp = city.temp
            weather = city.weather
            iconUrl = city.iconUrl
            windSpeed = city.windSpeed
            tempMin = city.tempMin
            tempMax = city.tempMax
            tempFeelsLike = city.tempFeelsLike
            humidity = city.humidity
        }
        if (cityDatabase?.getCityDao()?.initiateGetCity(temp.title) == null){
            cityDatabase?.getCityDao()?.initiateInsertCity(temp)
            cities.add(city)
        }

        textViewNoCities()
        initializeAdapter()
        initializeLinearLayoutManager()
    }

}