package kz.step.weathersoft.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.step.weathersoft.R
import kz.step.weathersoft.data.Weather
import kz.step.weathersoft.ui.viewholders.CityViewHolder
import kz.step.weathersoft.ui.viewholders.WeatherViewHolder

class WeatherAdapter(var weatherList: MutableList<Weather>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_weather_city, parent, false)
        return WeatherViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WeatherViewHolder).bind(weatherList.get(position))
    }

}