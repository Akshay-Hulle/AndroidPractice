package com.supremology.travelguide2.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.supremology.travelguide2.domain.model.Place


class SearchAdapter(
    private val onItemClick: (Place) -> Unit
) : ListAdapter<Place, SearchAdapter.SearchViewHolder>(DiffCallback()) {

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(place: Place) {
            nameText.text = place.name
            itemView.setOnClickListener {
                onItemClick(place)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Place, newItem: Place) = oldItem == newItem
    }
}
