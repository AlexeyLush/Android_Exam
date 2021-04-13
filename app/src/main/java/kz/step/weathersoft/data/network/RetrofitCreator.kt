package kz.step.weathersoft.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCreator {

    companion object{
        val BASE_URL = "https://api.openweathermap.org/"
    }

    fun initializeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun initializeApiInterface(): Api{
        return initializeRetrofit().create(Api::class.java)
    }

}