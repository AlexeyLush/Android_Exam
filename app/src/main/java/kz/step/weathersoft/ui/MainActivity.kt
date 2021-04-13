package kz.step.weathersoft.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kz.step.weathersoft.R
import kz.step.weathersoft.ui.fragments.CityFragment

class MainActivity : AppCompatActivity() {
    var fragmentContainer: FrameLayout? = null

    var currentFragment: Fragment? = null

    var fragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
        initializeViews()

        if (currentFragment == null) {
            initiateDisplayFragment(CityFragment(this))
        }
    }

    fun initializeViews() {
        fragmentContainer = findViewById(R.id.framelayout_activity_main_fragment_container)
    }

    fun initiateDisplayFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager
        currentFragment?.run {
            fragmentManager
                    ?.beginTransaction()
                    ?.hide(currentFragment!!)
        }


        if(!fragment.isAdded()){
            fragmentManager
                ?.beginTransaction()
                ?.add(R.id.framelayout_activity_main_fragment_container, fragment)
                ?.commit()
        }
        else{
            fragmentManager
                ?.beginTransaction()
                ?.hide(currentFragment!!)
                ?.show(fragment)
                ?.commit()
        }

        currentFragment = fragment

    }

}
