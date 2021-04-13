package kz.step.weathersoft.ui.contracts

import kz.step.weathersoft.data.City

class CityFragmentContract {

    interface View{

        fun initializeAdapter()

        fun initializeViews()

        fun initializeLinearLayoutManager()

    }


}