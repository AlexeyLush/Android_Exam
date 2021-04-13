package kz.step.weathersoft.data

import android.os.Parcel
import android.os.Parcelable

class City() :Parcelable {

    var title: String? = ""
    var country: String? = ""
    var temp: Float = 0f
    var weather: String? = ""
    var iconUrl: String? = ""

    var windSpeed: Float = 0f
    var tempMin: Float = 0f
    var tempMax: Float = 0f
    var tempFeelsLike: Float = 0f
    var humidity: Int = 0

    var lon: Float = 0.0f
    var lat: Float = 0.0f

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        country = parcel.readString()
        temp = parcel.readFloat()
        weather = parcel.readString()
        iconUrl = parcel.readString()
        windSpeed = parcel.readFloat()
        tempMin = parcel.readFloat()
        tempMax = parcel.readFloat()
        tempFeelsLike = parcel.readFloat()
        humidity = parcel.readInt()
        lon = parcel.readFloat()
        lat = parcel.readFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(country)
        parcel.writeFloat(temp)
        parcel.writeString(weather)
        parcel.writeString(iconUrl)
        parcel.writeFloat(windSpeed)
        parcel.writeFloat(tempMin)
        parcel.writeFloat(tempMax)
        parcel.writeFloat(tempFeelsLike)
        parcel.writeInt(humidity)
        parcel.writeFloat(lon)
        parcel.writeFloat(lat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }


}