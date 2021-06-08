package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.progressindicator.CircularProgressIndicator
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class StatsActivity : AppCompatActivity() {

    //https://api.covid19api.com/live/country/india/status/confirmed/date/2020-03-21T13:13:30Z
    private lateinit var recyclerView: RecyclerView
    private lateinit var states: ArrayList<State>
    private lateinit var requestQueue: RequestQueue
    private lateinit var progressBar: CircularProgressIndicator
    private lateinit var sharedPreferences: SharedPreferences

    private val preferenceKeyForDeaths = "deaths"
    private val preferenceKeyForRecovered = "recovered"
    private val preferenceKeyForConfirmed = "confirmed"
    private lateinit var headerSubTextView: TextView
    private lateinit var headerTextView: TextView
    private lateinit var recoveredHeadingTextView: TextView
    private lateinit var totalHeadingTextView: TextView
    private lateinit var deathHeadingTextView: TextView
    private lateinit var recoveredTextView: TextView
    private lateinit var deathsTextView: TextView
    private lateinit var totalTextView: TextView

    private fun fillStates() {
        states = ArrayList<State>()
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        val date = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        val url = "https://api.covid19api.com/live/country/india/status/confirmed/date/${year}-${month}-${date}T00:00:00Z"
        val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    progressBar.visibility = View.INVISIBLE
                    try {
                        val jsonArray = JSONArray(response)
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val name = jsonObject.getString("Province")
                            val confirmed = jsonObject.getString("Confirmed")
                            val deaths = jsonObject.getString("Deaths")
                            val active = jsonObject.getString("Active")
                            val recovered = jsonObject.getString("Recovered")
                            val state: State = State(name, confirmed, recovered, deaths, active)
                            states.add(state)
                        }
                        recyclerView.adapter = StateAdapter(applicationContext, states)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(applicationContext, "Live data not available, may be up soon", Toast.LENGTH_SHORT).show()
                        Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }) {
            Toast.makeText(applicationContext, "We can't fetch fresh data", Toast.LENGTH_SHORT).show()
        }

        requestQueue.add(stringRequest)
    }

    private fun setUpTextFromPreferences() {
        var text: String? = sharedPreferences.getString(preferenceKeyForConfirmed, "NA")
        totalTextView.text = text

        text = sharedPreferences.getString(preferenceKeyForRecovered, "NA")
        recoveredTextView.text = text

        text = sharedPreferences.getString(preferenceKeyForDeaths, "NA")
        deathsTextView.text = text
    }

    private fun init() {
        recoveredHeadingTextView = findViewById(R.id.recovered_cases_text)
        totalHeadingTextView = findViewById(R.id.total_cases_text)
        deathHeadingTextView = findViewById(R.id.death_cases_text)
        headerTextView = findViewById(R.id.live_stats_textview)
        headerSubTextView = findViewById(R.id.stats_subtext)

        val language = sharedPreferences.getString(getString(R.string.language), getString(R.string.language_english_key)) + ""

        if (language.contentEquals(getString(R.string.language_english_key))) {
            recoveredHeadingTextView.text = getString(R.string.recovered_english)
            totalHeadingTextView.text = getString(R.string.total_english)
            deathHeadingTextView.text = getString(R.string.death_english)
            headerTextView.text = getString(R.string.live_stats_english)
            headerSubTextView.text = getString(R.string.gets_updated_every_day_english)
        } else {
            recoveredHeadingTextView.text = getString(R.string.recovered_hindi)
            totalHeadingTextView.text = getString(R.string.total_hindi)
            deathHeadingTextView.text = getString(R.string.death_hindi)
            headerTextView.text = getString(R.string.live_stats_hindi)
            headerSubTextView.text = getString(R.string.gets_updated_every_day_hindi)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        supportActionBar?.hide()

        recoveredTextView = findViewById(R.id.recovered_no_text)
        deathsTextView = findViewById(R.id.death_no_text)
        totalTextView = findViewById(R.id.total_no_text)

        sharedPreferences = applicationContext.getSharedPreferences(getString(R.string.sharedPreferenceKey), MODE_PRIVATE)

        //Sets up text based on language
        init()
        progressBar = findViewById(R.id.progressbar)
        recyclerView = findViewById(R.id.stats_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        requestQueue = Volley.newRequestQueue(applicationContext)
        setUpTextFromPreferences()
        fillStates()
    }

    fun goBack(view: View) {
        finish()
    }
}