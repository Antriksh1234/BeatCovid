package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class ArticleFragment : Fragment() {

    private lateinit var ourArticlesHeadingTextView: TextView
    private lateinit var staySafeHeadingTextView: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var indicator: CircularProgressIndicator
    private lateinit var tip1Text: TextView
    private lateinit var tip2Text: TextView
    private lateinit var tip3Text: TextView
    private lateinit var tip4Text: TextView
    private lateinit var tip5Text: TextView
    private lateinit var tip6Text: TextView
    private lateinit var tip7Text: TextView

    private lateinit var subTip1Text: TextView
    private lateinit var subTip2Text: TextView
    private lateinit var subTip3Text: TextView
    private lateinit var subTip4Text: TextView
    private lateinit var subTip5Text: TextView
    private lateinit var subTip6Text: TextView
    private lateinit var subTip7Text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun init() {
        if (MainActivity.language.contentEquals(getString(R.string.language_english_key))) {
            staySafeHeadingTextView.text = getString(R.string.stay_safe_english)
            ourArticlesHeadingTextView.text = getString(R.string.our_articles_english)

            tip1Text.text = getString(R.string.maintain_social_distancing_english)
            tip2Text.text = getString(R.string.wear_mask_english)
            tip3Text.text = getString(R.string.wash_your_hands_english)
            tip4Text.text = getString(R.string.stay_at_home_english)
            tip5Text.text = getString(R.string.check_news_validity_english)
            tip6Text.text = getString(R.string.get_vaccinated_english)
            tip7Text.text = getString(R.string.help_others_english)

            subTip1Text.text = getString(R.string.maintain_social_distancing_big_english)
            subTip2Text.text = getString(R.string.wear_mask_big_english)
            subTip3Text.text = getString(R.string.wash_your_hands_big_english)
            subTip4Text.text = getString(R.string.stay_at_home_big_english)
            subTip5Text.text = getString(R.string.check_news_big_english)
            subTip6Text.text = getString(R.string.vaccine_safe_text_long_english)
            subTip7Text.text = getString(R.string.help_others_big_english)

        } else {
            staySafeHeadingTextView.text = getString(R.string.stay_safe_hindi)
            ourArticlesHeadingTextView.text = getString(R.string.our_articles_hindi)

            tip1Text.text = getString(R.string.maintain_social_distancing_hindi)
            tip2Text.text = getString(R.string.wear_mask_hindi)
            tip3Text.text = getString(R.string.wash_your_hands_hindi)
            tip4Text.text = getString(R.string.stay_at_home_hindi)
            tip5Text.text = getString(R.string.check_news_validity_hindi)
            tip6Text.text = getString(R.string.get_vaccinated_hindi)
            tip7Text.text = getString(R.string.help_others_hindi)

            subTip1Text.text = getString(R.string.maintain_social_distancing_big_hindi)
            subTip2Text.text = getString(R.string.wear_mask_big_hindi)
            subTip3Text.text = getString(R.string.wash_your_hands_big_hindi)
            subTip4Text.text = getString(R.string.stay_at_home_big_hindi)
            subTip5Text.text = getString(R.string.check_news_big_hindi)
            subTip6Text.text = getString(R.string.vaccine_safe_text_long_hindi)
            subTip7Text.text = getString(R.string.help_others_big_hindi)
        }
    }

    private fun fetchArticles() {
        val articles = ArrayList<Article>()
        val db = FirebaseFirestore.getInstance()
        if (MainActivity.language.contentEquals(getString(R.string.language_english_key))) {
            db.collection("Articles").whereEqualTo("isHindi", false).get()
                .addOnCompleteListener { task->
                    indicator.visibility = View.GONE
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val title = document.get("title") as String
                            val content: String = document.get("content") as String
                            val imageUrl = document.get("imageUrl") as String
                            val date = document.get("date") as String
                            val month = document.get("month") as String
                            val year = document.get("year") as String
                            val calDate = "$date $month $year"
                            articles.add(Article(title, content, imageUrl, calDate))
                        }
                        //Toast.makeText(context!!, "${articles.size}", Toast.LENGTH_SHORT).show()
                        try {
                            val adapter = ArticleAdapter(context!!, articles)
                            recyclerView.adapter = adapter
                        } catch (e: Exception) {
                            //Exception happened
                        }
                    } else {
                        //Not able to load new articles
                    }
                }
        } else {
            //Hindi articles
            db.collection("Articles").whereEqualTo("isHindi", true).get()
                .addOnCompleteListener { task->
                    indicator.visibility = View.GONE
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val title = document.get("title") as String
                            val content: String = document.get("content") as String
                            val imageUrl = document.get("imageUrl") as String
                            val date = document.get("date") as String
                            val month = document.get("month") as String
                            val year = document.get("year") as String
                            val calDate = "$date $month $year"
                            articles.add(Article(title, content, imageUrl, calDate))
                        }
                        try {
                            val adapter = ArticleAdapter(context!!, articles)
                            recyclerView.adapter = adapter
                        } catch (e: Exception) {
                            //Exception happened
                        }

                    } else {
                        //Not able to load new articles
                    }
                }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        recyclerView = view.findViewById(R.id.article_recyclerview)
        ourArticlesHeadingTextView = view.findViewById(R.id.our_articles_heading)
        staySafeHeadingTextView = view.findViewById(R.id.stay_safe_heading)
        indicator = view.findViewById(R.id.article_loading)
        tip1Text = view.findViewById(R.id.tips_text_1)
        tip2Text = view.findViewById(R.id.tips_text_2)
        tip3Text = view.findViewById(R.id.tips_text_3)
        tip4Text = view.findViewById(R.id.tips_text_4)
        tip5Text = view.findViewById(R.id.tips_text_5)
        tip6Text = view.findViewById(R.id.tips_text_6)
        tip7Text = view.findViewById(R.id.tips_text_7)

        subTip1Text = view.findViewById(R.id.tips_subtext_1)
        subTip2Text = view.findViewById(R.id.tips_subtext_2)
        subTip3Text = view.findViewById(R.id.tips_subtext_3)
        subTip4Text = view.findViewById(R.id.tips_subtext_4)
        subTip5Text = view.findViewById(R.id.tips_subtext_5)
        subTip6Text = view.findViewById(R.id.tips_subtext_6)
        subTip7Text = view.findViewById(R.id.tips_subtext_7)

        init()
        recyclerView.layoutManager = LinearLayoutManager(context)
        fetchArticles()
        return view
    }
}