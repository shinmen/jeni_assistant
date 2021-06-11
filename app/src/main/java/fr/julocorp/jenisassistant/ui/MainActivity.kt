package fr.julocorp.jenisassistant.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.ui.boitage.BoitageFragment
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        setCurrentFragment(CalendarFragment.newInstance())

        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_rendez_vous -> setCurrentFragment(CalendarFragment.newInstance())
                R.id.navigation_boitage -> setCurrentFragment(BoitageFragment())
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragment, fragment.tag)
            .addToBackStack(null)
            .commit()
    }
}