package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FAQAdapter(private var context: Context, val questions: ArrayList<FAQ_Question>) : RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.faq_item_layout, parent, false)
        return FAQViewHolder(view)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        holder.questionTextView.text = questions[position].question
        holder.answerTextView.text = questions[position].answer

        holder.dropDownImageView.setOnClickListener(View.OnClickListener {
            if (questions[position].isOpen) {
                questions[position].isOpen = !questions[position].isOpen
                holder.dropDownImageView.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                holder.answerTextView.visibility = View.GONE
            } else {
                questions[position].isOpen = !questions[position].isOpen
                holder.dropDownImageView.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                holder.answerTextView.visibility = View.VISIBLE
            }
        })
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    class FAQViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.faq_question)
        val answerTextView: TextView = itemView.findViewById(R.id.faq_answer)
        val dropDownImageView: ImageView = itemView.findViewById(R.id.dropdown_imageView)
    }
}