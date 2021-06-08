package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ThankYouActivity : AppCompatActivity() {

    private lateinit var thankYouTextView: TextView
    private lateinit var thankSubTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)
        supportActionBar?.hide()

        thankYouTextView = findViewById(R.id.thank_you)
        thankSubTextView = findViewById(R.id.thank_subtext)

        init()
    }

    private fun init() {
        if (MainActivity.language.contentEquals(getString(R.string.language_english_key))) {
            thankYouTextView.text = getString(R.string.thank_you_head)
            thankSubTextView.text = getString(R.string.we_will_work_on_it)
        } else {
            thankYouTextView.text = getString(R.string.thank_you_hindi)
            thankSubTextView.text = getString(R.string.we_will_work_on_it_hindi)
        }
    }

    fun finishActivity(view: View) {
        finish()
    }
}