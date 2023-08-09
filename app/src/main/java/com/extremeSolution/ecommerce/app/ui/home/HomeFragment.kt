package com.extremeSolution.ecommerce.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.extremeSolution.ecommerce.data.remote.networkLayer.NetworkManager
import com.extremeSolution.ecommerce.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var networkManager: NetworkManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        networkManager = NetworkManager(this.requireContext())

        initUI()
        viewModel.getCategoriesAndProductsAsync()


        return view
    }

    private fun initUI() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}