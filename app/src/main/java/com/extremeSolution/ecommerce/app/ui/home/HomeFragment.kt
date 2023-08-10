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
import com.extremeSolution.ecommerce.app.ui.home.adapters.products.ProductsAdapter
import com.extremeSolution.ecommerce.app.uiState.ErrorType
import com.extremeSolution.ecommerce.app.uiState.UiState
import com.extremeSolution.ecommerce.data.remote.networkLayer.NetworkManager
import com.extremeSolution.ecommerce.databinding.FragmentHomeBinding
import com.extremeSolution.ecommerce.domain.models.product.Product
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
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        networkManager = NetworkManager(this.requireContext())

        initUI()
        getCategoriesAndProducts()

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
                getCategoriesAndProducts()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun getCategoriesAndProducts() {
        showCategoriesLoading()
        showProductsLoading()
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

            viewModel.productsResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is UiState.Loading -> showProductsLoading()
                    is UiState.Success -> {
                        hideProductsLoading()
                        populateProductsRV(response.data)
                    }

                    is UiState.Error -> {
                        hideProductsLoading()

                        val errorMessage = when (response.errorType) {
                            ErrorType.EXCEPTION -> response.message.toString()
                            ErrorType.UNKNOWN -> getString(R.string.unidentified_error)
                            ErrorType.API_ERROR -> getString(R.string.request_is_not_successful)
                        }

                        showErrorProducts(errorMessage)
                    }
                }
            }

        } else {
            hideCategoriesLoading()
            hideProductsLoading()
            getString(R.string.not_connected).let {
                showErrorCategories(it)
                showErrorProducts(it)
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
        }
    }

    private fun hideCategoriesLoading() {
        binding.apply {
            progressBarCategories.makeInVisible()
            rvCategories.makeVisible()
            errorTvCategories.makeInVisible()
        }
    }

    private fun showErrorProducts(error: String) {
        binding.apply {
            errorLayoutProducts.makeVisible()
            progressBarProducts.makeInVisible()
            rvProducts.makeInVisible()
            errorTvProducts.text = error
        }
    }

    private fun showProductsLoading() {
        binding.apply {
            progressBarProducts.makeVisible()
        }
    }

    private fun hideProductsLoading() {
        binding.apply {
            progressBarProducts.makeInVisible()
            rvProducts.makeVisible()
            errorLayoutProducts.makeInVisible()
        }
    }

    private fun populateProductsRV(data: List<Product>?) {
        data?.let {
            if(!::productsAdapter.isInitialized){
                productsAdapter = ProductsAdapter()
                productsAdapter.setData(it)
                binding.rvProducts.adapter = productsAdapter
            } else {
                productsAdapter.setData(it)
            }
        }
    }
}