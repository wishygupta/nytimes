package com.mine.nytimesapi.ui.bestselller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mine.nytimesapi.R
import com.mine.nytimesapi.data.entities.BestSellers
import com.mine.nytimesapi.databinding.FragmentBestSellerBinding
import com.mine.nytimesapi.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BestSellerFragment : Fragment(),BestSellerSection.BestSellerItemClickListener {
    private val viewModel: BestSellerViewModel by viewModels()
    private lateinit var binding: FragmentBestSellerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBestSellerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupViews(weekly: List<BestSellers>, monthly: List<BestSellers>) {
        val sectionAdapter = SectionedRecyclerViewAdapter()
        sectionAdapter.addSection(BestSellerSection("WEEKLY", weekly,this))
        sectionAdapter.addSection(BestSellerSection("MONTHLY", monthly,this))
        binding.recyclerView.adapter = sectionAdapter
    }

    override fun onBestSellerClicked(encodedName: String) {
    }

    private fun setupObservers() {
        viewModel.fetchImages(getString(R.string.api_key)).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { bestsellers ->
                            val weekly = bestsellers.filter { it.updated.equals("WEEKLY") }
                                .sortedBy {
                                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                                    val date: Date = sdf.parse(it.publishDate)
                                    val millis: Long = date.getTime()
                                    millis
                                }.reversed()
                            val monthly = bestsellers.filter { it.updated.equals("MONTHLY") }
                                .sortedBy {
                                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                                    val date: Date = sdf.parse(it.publishDate)
                                    val millis: Long = date.getTime()
                                    millis
                                }.reversed()
                            setupViews(weekly, monthly)
                        }
                    }
                    Resource.Status.ERROR -> {

                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE

                    }
                }
            }
        })
    }
}