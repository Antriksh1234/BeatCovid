package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView

class SplashScreen : AppCompatActivity() {
    private lateinit var headingTextView: TextView
    private lateinit var subTextView: TextView
    private lateinit var sp: SharedPreferences

    private fun init() {
        val lang = sp.getString(getString(R.string.language), getString(R.string.language_english_key)) + ""
        if (lang.contentEquals(getString(R.string.language_english_key))) {
            subTextView.text = getString(R.string.chain_english)
            headingTextView.text = getString(R.string.we_can_do_it_english)
        } else {
            subTextView.text = getString(R.string.chain_hindi)
            headingTextView.text = getString(R.string.we_can_do_it_hindi)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sp = getSharedPreferences(getString(R.string.sharedPreferenceKey), Context.MODE_PRIVATE)
        subTextView = findViewById(R.id.subTextView)
        headingTextView = findViewById(R.id.mainTextView)

        init()
        val timer: CountDownTimer = object : CountDownTimer(1500, 500) {
            override fun onTick(p0: Long) {
                //Noting
            }

            override fun onFinish() {
                finish()
                val intent: Intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }

        timer.start()
    }
}