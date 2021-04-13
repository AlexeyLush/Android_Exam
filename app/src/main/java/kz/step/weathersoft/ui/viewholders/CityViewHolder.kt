package kz.step.weathersoft.ui.viewholders

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import kz.step.weathersoft.R
import kz.step.weathersoft.data.City
import kz.step.weathersoft.domain.UseCaseFindIcon
import kz.step.weathersoft.ui.MainActivity
import kz.step.weathersoft.ui.adapters.CitiesAdapter
import kz.step.weathersoft.ui.fragments.CityFragment
import kz.step.weathersoft.ui.fragments.WeatherFragment

class CityViewHolder(itemView: View, var citiesAdapter: CitiesAdapter, var mainActivity: MainActivity, var cityFragment: CityFragment) : RecyclerView.ViewHolder(itemView) {

    var textViewCityTitleCountry: TextView? = null
    var textViewCityTempWeather: TextView? = null
    var imageViewCityIcon: ImageView? = null
    var imageViewDeleteCity: ImageView? = null
    var cardView: CardView? = null

    init {
        textViewCityTitleCountry = itemView.findViewById(R.id.textview_viewholder_city_title_country)
        textViewCityTempWeather = itemView.findViewById(R.id.textview_viewholder_city_temp_weather)
        imageViewCityIcon = itemView.findViewById(R.id.imageview_viewholder_city_icon)
        imageViewDeleteCity = itemView.findViewById(R.id.imageview_viewholder_city_delete)
        cardView = itemView.findViewById(R.id.cardview_viewholder_city)
    }

    fun bind(city: City){

        imageViewDeleteCity?.setOnClickListener{
            citiesAdapter.removeCity(position = position)
        }

        cardView?.setOnClickListener {
            mainActivity.initiateDisplayFragment(WeatherFragment(citiesAdapter.citiesList.get(position),cityFragment = cityFragment, mainActivity = mainActivity))
        }

        textViewCityTitleCountry?.text = "${city.title}, ${city.country}"
        textViewCityTempWeather?.text = "${city.temp.toInt()} °C, ${city.weather}"

        imageViewCityIcon?.setImageResource(UseCaseFindIcon().initiateIcon(city.iconUrl!!))

    }

}