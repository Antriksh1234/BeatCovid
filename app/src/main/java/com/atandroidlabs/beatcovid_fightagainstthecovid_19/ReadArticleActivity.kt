package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ReadArticleActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var articleImageView: ImageView
    private lateinit var publishedTextView: TextView
    private lateinit var contentTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_article)

        supportActionBar?.hide()

        titleTextView = findViewById(R.id.article_title)
        articleImageView = findViewById(R.id.article_main_img)
        contentTextView = findViewById(R.id.article_content)
        publishedTextView = findViewById(R.id.timestamp)

        val title: String = "" + intent.getStringExtra("title")
        val content: String = "" + intent.getStringExtra("content")
        val url = "" + intent.getStringExtra("url")
        val published = "" + intent.getStringExtra("date")

        "Published $published".also { publishedTextView.text = it }
        titleTextView.text = title
        contentTextView.text = content

        Picasso.get().load(url).placeholder(R.drawable.ic_baseline_menu_book_24).error(R.drawable.ic_baseline_menu_book_24).into(articleImageView)
    }
}