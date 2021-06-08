package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ContentAdapter(private var context: Context, private val contentList: ArrayList<Content>) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.content_item_layout, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val title = contentList[position].title
        holder.contentTitleTextView.ellipsize = TextUtils.TruncateAt.END
        holder.contentTitleTextView.maxLines = 2
        holder.contentTitleTextView.text = title
        Picasso.get().load(contentList[position].thumbnailUrl).placeholder(R.drawable.ic_baseline_subscriptions_24).error(R.drawable.ic_baseline_subscriptions_24).into(holder.imageView)

        holder.playButton.setOnClickListener(View.OnClickListener {
            try {
                val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(contentList[position].url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentTitleTextView: TextView = itemView.findViewById(R.id.content_title)
        val imageView: ImageView = itemView.findViewById(R.id.photo_thumbnail)
        val playButton: Button = itemView.findViewById(R.id.play_button)
    }
}