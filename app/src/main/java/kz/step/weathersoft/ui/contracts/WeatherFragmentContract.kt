package kz.step.weathersoft.ui.contracts

import kz.step.weathersoft.data.Weather

class WeatherFragmentContract {

    interface View{
        fun initializeViews()

        fun initializeListeners()

        fun initializeValues()

        fun initializeAdapter(weatherList: MutableList<Weather>)

        fun initializeLinearLayoutManager()
    }

}