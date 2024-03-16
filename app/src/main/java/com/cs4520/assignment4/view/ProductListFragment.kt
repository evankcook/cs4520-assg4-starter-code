package com.cs4520.assignment4.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cs4520.assignment4.ProductViewModel
import com.cs4520.assignment4.databinding.ProductListFragmentBinding


class ProductListFragment : Fragment() {
    private var _binding: ProductListFragmentBinding? = null
    private val binding get() = _binding!!

    private var viewModel: ProductViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        viewModel!!.getProducts()
        initObserver()
    }

    private fun initObserver() {
        viewModel?.products?.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.productRecyclerView.visibility = View.VISIBLE
                binding.noProducts.visibility = View.GONE
            } else {
                binding.productRecyclerView.visibility = View.GONE
                binding.noProducts.visibility = View.VISIBLE
            }
            binding.productRecyclerView.adapter = ProductListAdapter(it)
        }
        viewModel?.isLoading?.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility =View.VISIBLE
            } else {
                binding.progressBar.visibility =View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}