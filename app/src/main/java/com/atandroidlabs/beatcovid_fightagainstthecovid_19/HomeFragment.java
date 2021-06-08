package com.atandroidlabs.beatcovid_fightagainstthecovid_19;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AsyncRequestQueue;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private TextView recoveredTextView, deathsTextView, confirmedTextView, viewAllTextView, headerTextView, headerSubTextView, recoveredHeadingTextView, deathHeadingTextView, totalHeadingTextView, statsTextView;
    private RequestQueue requestQueue;
    private final String preferenceKeyForDeaths = "deaths";
    private final String preferenceKeyForRecovered = "recovered";
    private final String preferenceKeyForConfirmed = "confirmed";
    SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = requireContext().getSharedPreferences(getString(R.string.sharedPreferenceKey), Context.MODE_PRIVATE);
    }

    private void init() {
        String lang = preferences.getString(getString(R.string.language), getString(R.string.language_english_key));
        if (lang.contentEquals(getString(R.string.language_english_key))) {
            headerTextView.setText("Let's beat\nCoronavirus");
            headerSubTextView.setText(getString(R.string.neither_get_infected_nor_infect_others_english));
            totalHeadingTextView.setText(getString(R.string.total_cases_english));
            recoveredHeadingTextView.setText(getString(R.string.recovered_english));
            deathHeadingTextView.setText(getString(R.string.death_english));
            viewAllTextView.setText(getString(R.string.view_all_english));
            statsTextView.setText(getString(R.string.stats_for_nerds_english));
        } else {
            headerTextView.setText(getString(R.string.lets_beat_covid_hindi));
            headerSubTextView.setText(getString(R.string.neither_get_infected_nor_infect_others_hindi));
            totalHeadingTextView.setText(getString(R.string.total_cases_hindi));
            recoveredHeadingTextView.setText(getString(R.string.recovered_hindi));
            deathHeadingTextView.setText(getString(R.string.death_hindi));
            viewAllTextView.setText(getString(R.string.view_all_hindi));
            statsTextView.setText(getString(R.string.stats_for_nerds_hindi));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recoveredTextView = view.findViewById(R.id.recovered_no_text);
        deathsTextView = view.findViewById(R.id.death_no_text);
        confirmedTextView = view.findViewById(R.id.total_no_text);
        viewAllTextView = view.findViewById(R.id.view_all_textView);

        headerTextView = view.findViewById(R.id.beat_covid_text);
        headerSubTextView = view.findViewById(R.id.beat_covid_subtext);
        totalHeadingTextView = view.findViewById(R.id.total_cases_text);
        recoveredHeadingTextView = view.findViewById(R.id.recovered_cases_text);
        deathHeadingTextView = view.findViewById(R.id.death_cases_text);
        statsTextView = view.findViewById(R.id.heading2);

        init();

        viewAllTextView.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireContext(), StatsActivity.class);
            startActivity(intent);
        });

        if (isNetworkAvailable()) {
            requestQueue = Volley.newRequestQueue(requireContext());
            getCOVIDDataFromApi();
        } else {
            setUpTextFromPreferences(1);
            setUpTextFromPreferences(2);
            setUpTextFromPreferences(3);
            Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }

    private void getCOVIDDataFromApi() {
        //To get total no we need to set from previous date to today for entire numbers
        Calendar calendar = Calendar.getInstance();

        //To avoid the morning data being not available
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            calendar.add(Calendar.DATE, -1);
        }

        int toDate = calendar.get(Calendar.DAY_OF_MONTH);
        int toYear = calendar.get(Calendar.YEAR);
        int toMonth = calendar.get(Calendar.MONTH) + 1;

        calendar.add(Calendar.DATE, -1);
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        //eg from 2021-5-15T00:00:00Z
        String fromPart = year + "-" + month + "-" + date + "T00:00:00Z";
        String toPart = toYear + "-" + toMonth + "-" + toDate + "T00:00:00Z";

        String deathsUrl = "https://api.covid19api.com/country/india/status/deaths?from=" + fromPart + "&to=" + toPart;
        String recoveredUrl = "https://api.covid19api.com/country/india/status/recovered?from=" + fromPart + "&to=" + toPart;
        String totalUrl = "https://api.covid19api.com/country/india/status/confirmed?from=" + fromPart + "&to=" + toPart;

        getCOVIDDataIndividual(deathsUrl, 3);
        getCOVIDDataIndividual(recoveredUrl, 2);
        getCOVIDDataIndividual(totalUrl, 1);
    }

    //1 for confirmed, 2 for recovered, 3 for deaths
    void getCOVIDDataIndividual(String url, int which) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String result = jsonObject.getString("Cases");
                                setAppropriateTextView(result, which);
                            } else {
                                setUpTextFromPreferences(which);
                            }
                        } catch (Exception e) {
                            setUpTextFromPreferences(which);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setUpTextFromPreferences(which);
            }
        });

        requestQueue.add(stringRequest);
    }

    //1 for confirmed, 2 for recovered, 3 for deaths
    private void setUpTextFromPreferences(int which) {
        String text;
        if (which == 1) {
            text = preferences.getString(preferenceKeyForConfirmed, "NA");
            confirmedTextView.setText(text);
        }
        else if (which == 2) {
            text = preferences.getString(preferenceKeyForRecovered, "NA");
            recoveredTextView.setText(text);
        } else {
            text = preferences.getString(preferenceKeyForDeaths, "NA");
            deathsTextView.setText(text);
        }
    }

    //1 for confirmed, 2 for recovered, 3 for deaths
    private void setAppropriateTextView(String result, int which) {
        double value = Double.parseDouble(result);
        if (value == 0) {
            result = "NA";
            try {
                Toast.makeText(getContext(), "Currently New data is not up", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Currently New data is not up", Toast.LENGTH_SHORT).show();
            }
        } else if (value > 100000 && value < 10000000) {
            value /= 100000.00;
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.HALF_UP);
            result = df.format(value) + " L";
        } else if (value > 10000000) {
            value /= 10000000.00;
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            result = df.format(value) + " Cr";
        }

        //1 for confirmed, 2 for recovered, 3 for deaths
        if (which == 1) {
            confirmedTextView.setText(result);
            preferences.edit().putString(preferenceKeyForConfirmed, result).apply();
        } else if (which == 2) {
            recoveredTextView.setText(result);
            preferences.edit().putString(preferenceKeyForRecovered, result).apply();
        } else {
            deathsTextView.setText(result);
            preferences.edit().putString(preferenceKeyForDeaths, result).apply();
        }
    }
}