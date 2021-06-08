package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class SuggestionsActivity : AppCompatActivity() {

    private lateinit var titleTextField: TextInputEditText
    private lateinit var contentTextField: TextInputEditText
    private lateinit var feedbackHeading: TextView
    private lateinit var suggestionSubTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestions)
        supportActionBar?.hide()

        titleTextField = findViewById(R.id.title_textfield)
        contentTextField = findViewById(R.id.content_textfield)

        feedbackHeading = findViewById(R.id.suggestion_heading)
        suggestionSubTextView = findViewById(R.id.suggestion_subtext)

        init()
    }

    private fun init() {
        if (MainActivity.language.contentEquals(getString(R.string.language_english_key))) {
            feedbackHeading.text = getString(R.string.feedback_head)
            suggestionSubTextView.text = getString(R.string.you_can_write_us)
        } else {
            feedbackHeading.text = getString(R.string.feedback_head_hindi)
            suggestionSubTextView.text = getString(R.string.you_can_write_us_hindi)
        }
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

    fun submit(view: View) {

        if (isNetworkAvailable()) {
            val title = titleTextField.text.toString()
            val content = contentTextField.text.toString()

            if (title.contentEquals("") || content.contentEquals("")) {
                Toast.makeText(this, "Please enter full details", Toast.LENGTH_SHORT).show()
            } else {
                val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                val map = HashMap<String, String>()

                val intent = Intent(applicationContext, ThankYouActivity::class.java)
                startActivity(intent)
                finish()

                map["title"] = title
                map["content"] = content
                db.collection("suggestions").add(map)
                    .addOnCompleteListener(OnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(applicationContext, "We got your feedback", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, "We couldn't get your feedback", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        } else {
           Toast.makeText(applicationContext, "Please check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }
}