package kz.step.weathersoft.domain

import kz.step.weathersoft.R

class UseCaseFindIcon() {

    fun initiateIcon(icon: String): Int{
        if (icon == "01d"){
            return(R.drawable.icon01d)
        }
        else if(icon == "01n"){
            return(R.drawable.icon01n)
        }

        else if (icon == "02d"){
            return(R.drawable.icon02d)
        }
        else if(icon == "02n"){
            return(R.drawable.icon02n)
        }

        else if (icon == "03d"){
            return(R.drawable.icon03d)
        }
        else if(icon == "03n"){
            return(R.drawable.icon03n)
        }

        else if (icon == "04d"){
            return(R.drawable.icon04d)
        }
        else if(icon == "04n"){
            return(R.drawable.icon04n)
        }

        else if (icon == "09d"){
            return(R.drawable.icon09d)
        }
        else if(icon == "09n"){
            return(R.drawable.icon09n)
        }

        else if (icon == "10d"){
            return(R.drawable.icon10d)
        }
        else if(icon == "10n"){
            return(R.drawable.icon10n)
        }

        else if (icon == "11d"){
            return(R.drawable.icon11d)
        }
        else if(icon == "11n"){
            return(R.drawable.icon11n)
        }

        else if (icon == "13d"){
            return(R.drawable.icon13d)
        }
        else if(icon == "13n"){
            return(R.drawable.icon13n)
        }

        else if (icon == "50d"){
            return(R.drawable.icon13d)
        }
        else if(icon == "50n"){
            return(R.drawable.icon13n)
        }

        return 0
    }

}