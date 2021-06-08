package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var fragment: androidx.fragment.app.Fragment
    private lateinit var homeFragment: HomeFragment
    private lateinit var articleFragment: ArticleFragment
    private lateinit var contentFragment: ContentFragment
    private lateinit var vaccinationFragment: VaccinationFragment
    private lateinit var sharedPreferences: SharedPreferences
    companion object {
        lateinit var language: String
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw)
            actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(
                NetworkCapabilities.TRANSPORT_BLUETOOTH
            ))
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo
            nwInfo != null && nwInfo.isConnected
        }
    }

    private fun init() {
        sharedPreferences = getSharedPreferences(
            getString(R.string.sharedPreferenceKey),
            Context.MODE_PRIVATE
        )

        language = sharedPreferences.getString(
            getString(R.string.language),
            getString(R.string.language_english_key)
        ) + ""
        homeFragment = HomeFragment()
        articleFragment = ArticleFragment()
        contentFragment = ContentFragment()
        vaccinationFragment = VaccinationFragment()
        fragment = homeFragment

        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out
        ).replace(R.id.fragment_container, fragment).commit()
        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        if (isNetworkAvailable()) {
            init()
        } else {
            showAlertDialogOfInternet();
        }
    }

    private fun showAlertDialogOfInternet() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val view: View = LayoutInflater.from(this).inflate(R.layout.internet_dialog, null, false)
        val retryButton: MaterialButton = view.findViewById(R.id.retry_button)
        builder.setView(view)
        val alertDialog = builder.create()
        retryButton.setOnClickListener {
            if (isNetworkAvailable()) {
                alertDialog.dismiss()
                init()
            } else {
                //Do nothing
                view.invalidate()
            }
        }

        alertDialog.show()
    }

    private val navListener : BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    fragment = homeFragment
                }

                R.id.nav_articles -> {
                    fragment = articleFragment
                }

                R.id.nav_content_of_the_week -> {
                    fragment = contentFragment
                }

                R.id.nav_vaccination_and_testing -> {
                    fragment = vaccinationFragment
                }

                else -> {
                    fragment = homeFragment
                }
            }

            supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out
            ).replace(R.id.fragment_container, fragment).commit()
            true
        }

    fun goToSettings(view: View) {
        val intent: Intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goToFeedback(view: View) {
        val intent: Intent = Intent(this, SuggestionsActivity::class.java)
        startActivity(intent)
    }
}