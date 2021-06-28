package com.mine.nytimesapi.ui.books

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mine.nytimesapi.R
import com.mine.nytimesapi.databinding.FragmentBooksBinding
import com.mine.nytimesapi.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BooksFragment : Fragment() {


    private val viewModel: BooksViewModel by viewModels()
    private val booksAdapter=BooksAdapter()

    private lateinit var binding: FragmentBooksBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter=booksAdapter
        arguments?.getString("book_id")?.let {
            setupObservers(it)
        }

    }

    private fun setupObservers(bookId: String) {
        viewModel.fetchBooks(getString(R.string.api_key), bookId).observe(
            viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            resource.data?.let {
                               booksAdapter.setItems(ArrayList(it))
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
            }
        )
    }

}