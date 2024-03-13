package com.cs4520.assignment4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cs4520.assignment4.databinding.ProductListFragmentBinding


class ProductListFragment : Fragment() {
    private var _binding: ProductListFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = ProductListFragmentBinding.inflate(layoutInflater)

        val productList = Util.createProductList(productsDataset)
        binding.productRecyclerView.adapter = ProductListAdapter(productList)

        return binding.root
    }

