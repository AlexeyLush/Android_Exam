package kz.step.weathersoft.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.step.weathersoft.R
import kz.step.weathersoft.data.Weather
import kz.step.weathersoft.domain.UseCaseFindIcon


class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
    var imageViewIcon: ImageView? = null
    var textViewTempDay: TextView? = null
    var textViewTempNight: TextView? = null
    
    init {
        imageViewIcon = itemView.findViewById(R.id.imageView_viewholder_weather_city_icon)
        textViewTempDay = itemView.findViewById(R.id.textView_viewholder_weather_city_temp_day)
        textViewTempNight = itemView.findViewById(R.id.textView_viewholder_weather_city_temp_night)
    }
    
    fun bind(weather: Weather){

        textViewTempDay?.text = "Day: ${weather.day?.toInt()}°C"
        textViewTempNight?.text = "Night: ${weather.night?.toInt()}°C"

        imageViewIcon?.setImageResource(UseCaseFindIcon().initiateIcon(weather.icon!!))
    }
    
}