package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class ArticleAdapter(private var context: Context, private val articles: ArrayList<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.article_item_layout, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val title: String = articles[position].title
        val content: String = articles[position].content
        val url = articles[position].imageUrl
        val date = articles[position].publishDate
        holder.articleTitleTextView.text = title
        holder.publishedTextView.text = date
        Picasso.get().load(url).placeholder(R.drawable.ic_baseline_menu_book_24).error(R.drawable.ic_baseline_menu_book_24).into(holder.imageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ReadArticleActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            intent.putExtra("date", date)
            intent.putExtra("url", url)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val articleTitleTextView: TextView = itemView.findViewById(R.id.article_heading)
        val publishedTextView: TextView = itemView.findViewById(R.id.published_date)
        val imageView: ImageView = itemView.findViewById(R.id.article_image)
    }
}