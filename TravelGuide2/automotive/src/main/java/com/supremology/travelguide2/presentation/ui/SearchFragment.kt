package com.supremology.travelguide2.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supremology.travelguide2.R
import com.supremology.travelguide2.presentation.adapter.SearchAdapter
import com.supremology.travelguide2.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var searchInput: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchInput = view.findViewById(R.id.search_input)
        recyclerView = view.findViewById(R.id.suggestions_recycler)

        adapter = SearchAdapter { place ->
            val bundle = Bundle().apply {
                putString("place_name", place.name)
                putDouble("lat", place.lat)
                putDouble("lon", place.lon)
            }

            findNavController().navigate(
                R.id.action_searchFragment_to_mapFragment,
                bundle
            )
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchInput.addTextChangedListener {
            viewModel.search(it.toString())
        }

        viewModel.suggestions.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return view
    }
}
