package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class ContentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var titleTextView: TextView
    private lateinit var subtextView: TextView
    private lateinit var indicator: CircularProgressIndicator

    private fun fetchContent() {
        val contentList: ArrayList<Content> = ArrayList<Content>()

        val db = FirebaseFirestore.getInstance()
        db.collection("Content").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.id
                        val title: String = "" + document.get("title").toString()
                        val contentLink = "" + document.get("link").toString()
                        val thumbnailUrl = "" + document.get("thumbnailUrl")

                        contentList.add(Content(title, contentLink, thumbnailUrl))
                    }

                    try {
                        if (context != null) {
                            recyclerView.layoutManager = LinearLayoutManager(context)
                            recyclerView.adapter = ContentAdapter(context!!, contentList)
                        }
                    } catch (e: Exception) {
                        //Exception happened
                    }

                } else {
                    //Exception happened
                }
                indicator.visibility = View.GONE

            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)
        titleTextView = view.findViewById(R.id.content_title_heading)
        subtextView = view.findViewById(R.id.content_head_subtext)
        indicator = view.findViewById(R.id.content_loading)
        init()

        recyclerView = view.findViewById(R.id.content_recyclerview)
        fetchContent()
        return view
    }

    private fun init() {
        if (MainActivity.language.contentEquals(getString(R.string.language_english_key))) {
            titleTextView.text = getString(R.string.content_we_loved)
            subtextView.text = getString(R.string.content_we_loved__sub_english)
        } else {
            titleTextView.text = getString(R.string.content_we_loved_hindi)
            subtextView.text = getString(R.string.content_we_loved_sub_hindi)
        }
    }
}