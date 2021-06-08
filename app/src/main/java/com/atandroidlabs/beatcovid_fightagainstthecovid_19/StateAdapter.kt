package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateAdapter(val context: Context, val states: ArrayList<State>) : RecyclerView.Adapter<StateAdapter.StateViewHolder>() {

    class StateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val provinceNameTextView: TextView = itemView.findViewById(R.id.province_name)
        val recoveredTextView: TextView = itemView.findViewById(R.id.recovered_stats_number)
        val deathTextView: TextView = itemView.findViewById(R.id.deaths_stats_number)
        val activeTextView: TextView = itemView.findViewById(R.id.active_stats_number)
        val confirmedTextView: TextView = itemView.findViewById(R.id.confirmed_stats_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.stats_item_layout, parent, false)
        return StateViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.provinceNameTextView.text = states[position].stateName
        holder.activeTextView.text = states[position].active
        holder.confirmedTextView.text = states[position].confirmed
        holder.deathTextView.text = states[position].deaths
        holder.recoveredTextView.text = states[position].recovered
    }

    override fun getItemCount(): Int {
        return states.size
    }
}