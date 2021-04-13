package kz.step.weathersoft.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.step.weathersoft.R
import kz.step.weathersoft.data.City
import kz.step.weathersoft.ui.MainActivity
import kz.step.weathersoft.ui.fragments.CityFragment
import kz.step.weathersoft.ui.viewholders.CityViewHolder

class CitiesAdapter(var citiesList: MutableList<City>, var cityFragment: CityFragment, var mainActivity: MainActivity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_city, parent, false)
        return CityViewHolder(itemView, this, mainActivity, cityFragment)
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }

    fun removeCity(position: Int){
        cityFragment.removeCity(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CityViewHolder).bind(citiesList.get(position))
    }
}