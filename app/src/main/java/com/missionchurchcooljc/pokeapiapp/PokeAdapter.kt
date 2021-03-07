package com.missionchurchcooljc.pokeapiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PokeAdapter(private val pokes: PokeResponse) : RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {


    class PokeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PokeViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.poke_list_item, viewGroup, false)
        return PokeViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: PokeViewHolder, position: Int) {
        viewHolder.textView.text = pokes.pokeList[position].name
    }

    override fun getItemCount() = pokes.pokeList.size
}