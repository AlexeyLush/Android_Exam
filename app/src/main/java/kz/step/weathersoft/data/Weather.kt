package kz.step.weathersoft.data

import android.os.Parcel
import android.os.Parcelable


class Weather() : Parcelable {

    var city: String? = null

    var day: Float? = null
    var night: Float? = null


    var icon: String? = null

    constructor(parcel: Parcel) : this() {
        city = parcel.readString()
        day = parcel.readValue(Float::class.java.classLoader) as? Float
        night = parcel.readValue(Float::class.java.classLoader) as? Float
        icon = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(city)
        parcel.writeValue(day)
        parcel.writeValue(night)
        parcel.writeString(icon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }


}