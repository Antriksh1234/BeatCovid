package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class SettingsActivity : AppCompatActivity() {

    private lateinit var hindiCardView: CardView
    private lateinit var englishCardView: CardView
    private lateinit var hindiPrimaryTextView: TextView
    private lateinit var englishPrimaryTextView: TextView
    private lateinit var hindiSecondaryTextView: TextView
    private lateinit var englishSecondaryTextView: TextView
    private lateinit var headerTextView: TextView
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportActionBar?.hide()

        hindiCardView = findViewById(R.id.hindi_cardView)
        englishCardView = findViewById(R.id.english_cardView)
        hindiPrimaryTextView = findViewById(R.id.hindiPrimary)
        hindiSecondaryTextView = findViewById(R.id.hindiSecondary)
        englishPrimaryTextView = findViewById(R.id.englishPrimary)
        englishSecondaryTextView = findViewById(R.id.englishSecondary)
        headerTextView = findViewById(R.id.choose_lang)

        sp = this.getSharedPreferences(getString(R.string.sharedPreferenceKey), Context.MODE_PRIVATE)
        setAppropriateCardAsColored()
    }

    //We also change the header here according to language
    private fun setAppropriateCardAsColored() {
        val language = sp.getString(getString(R.string.language), "English") + ""
        if (language.contentEquals(getString(R.string.language_english_key))) {
            englishCardView.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.themeColor))
            englishPrimaryTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            englishSecondaryTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))

            hindiCardView.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
            hindiPrimaryTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            hindiSecondaryTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))

            headerTextView.text = getString(R.string.choose_lang_english)
        } else {
            //hindi is default right now
            hindiCardView.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.themeColor))
            hindiPrimaryTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            hindiSecondaryTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))

            englishCardView.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
            englishPrimaryTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            englishSecondaryTextView.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            headerTextView.text = getString(R.string.choose_language_hindi)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun changeLanguage(view: View) {
        val tag: String = view.tag.toString()
        if (tag.contentEquals("1")) {
            //it is hindi card so hindi is selected
            sp.edit().putString(getString(R.string.language), getString(R.string.language_hindi_key)).apply()
            MainActivity.language = getString(R.string.language_hindi_key)
        } else {
            //english card selected
            sp.edit().putString(getString(R.string.language), getString(R.string.language_english_key)).apply()
            MainActivity.language = getString(R.string.language_english_key)
        }
        setAppropriateCardAsColored()
    }

    fun goBack(view: View) {
        val intent = Intent(this, MainActivity::class.java);
        startActivity(intent)
        finish()
    }
}