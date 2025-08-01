package com.supremology.travelguide2.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.supremology.travelguide2.R
import com.supremology.travelguide2.domain.model.Place
//
//class NearbyPlacesAdapter(
//    private val places: List<Place>,
//    private val onPlaceClick: (Place) -> Unit
//) : RecyclerView.Adapter<NearbyPlacesAdapter.ViewHolder>() {
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(place: Place) {
//            itemView.findViewById<TextView>(R.id.placeName).text = place.name
//            itemView.setOnClickListener {
//                onPlaceClick(place)
//            }
//        }
//        val imageView: ImageView = view.findViewById(R.id.placeImage)
//        val nameText: TextView = view.findViewById(R.id.placeName)
//        val categoryText: TextView = view.findViewById(R.id.placeCategory)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_nearby_place, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int = places.size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val place = places[position]
//        holder.nameText.text = place.name
//        holder.categoryText.text = place.category
//        holder.imageView.load(place.imageUrl) {
//            placeholder(R.drawable.ic_placeholder)
//            error(R.drawable.ic_placeholder)
//        }
//
//        // Set the click listener on the entire item view
//        holder.itemView.setOnClickListener {
//            onPlaceClick(place)
//        }
//    }
//}

class NearbyPlacesAdapter(
    private val places: List<Place>,
    private val onPlaceClick: (Place) -> Unit
) : RecyclerView.Adapter<NearbyPlacesAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.placeImage)
        private val nameText: TextView = view.findViewById(R.id.placeName)
        private val categoryText: TextView = view.findViewById(R.id.placeCategory)

        fun bind(place: Place) {
            nameText.text = place.name
            categoryText.text = place.category
            imageView.load(place.imageUrl) {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
            }
            itemView.setOnClickListener {
                onPlaceClick(place)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nearby_place, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(places[position])
    }
}

