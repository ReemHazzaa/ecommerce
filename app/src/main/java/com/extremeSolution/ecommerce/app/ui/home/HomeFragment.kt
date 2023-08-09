package com.extremeSolution.ecommerce.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.extremeSolution.ecommerce.R
import com.extremeSolution.ecommerce.app.extensions.makeInVisible
import com.extremeSolution.ecommerce.app.extensions.makeVisible
import com.extremeSolution.ecommerce.app.extensions.showSnackBar
import com.extremeSolution.ecommerce.app.ui.home.adapters.categories.CategoriesAdapter
import com.extremeSolution.ecommerce.app.uiState.ErrorType
import com.extremeSolution.ecommerce.app.uiState.UiState
import com.extremeSolution.ecommerce.data.remote.networkLayer.NetworkManager
import com.extremeSolution.ecommerce.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var networkManager: NetworkManager
    private val categoriesAdapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        networkManager = NetworkManager(this.requireContext())

        initUI()

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initUI() {
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = true
                getCategories()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun getCategories() {
        showCategoriesLoading()
        if (networkManager.isNetworkAvailable()) {

            viewModel.getCategoriesAndProductsAsync()
            viewModel.categoriesResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is UiState.Loading -> showCategoriesLoading()
                    is UiState.Success -> {
                        hideCategoriesLoading()
                        populateCategoriesRV(response.data)
                    }

                    is UiState.Error -> {
                        hideCategoriesLoading()

                        val errorMessage = when (response.errorType) {
                            ErrorType.EXCEPTION -> response.message.toString()
                            ErrorType.UNKNOWN -> getString(R.string.unidentified_error)
                            ErrorType.API_ERROR -> getString(R.string.request_is_not_successful)
                        }

                        showErrorCategories(errorMessage)
                    }
                }
            }


        } else {
            hideCategoriesLoading()
            getString(R.string.not_connected).let {
                showErrorCategories(it)
                requireActivity().showSnackBar(it)
            }
        }
    }

    private fun populateCategoriesRV(data: List<String>?) {
        data?.let {
            categoriesAdapter.setData(it)
            binding.rvCategories.adapter = categoriesAdapter
        }
    }

    private fun showErrorCategories(error: String) {
        binding.apply {
            errorTvCategories.makeVisible()
            progressBarCategories.makeInVisible()
            rvCategories.makeInVisible()
            errorTvCategories.text = error
        }
    }

    private fun showCategoriesLoading() {
        binding.apply {
            progressBarCategories.makeVisible()
            errorTvCategories.makeInVisible()
            rvCategories.makeInVisible()
        }
    }

    private fun hideCategoriesLoading() {
        binding.apply {
            progressBarCategories.makeInVisible()
            errorTvCategories.makeInVisible()
            rvCategories.makeVisible()
        }
    }
}