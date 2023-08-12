package com.extremeSolution.ecommerce.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.extremeSolution.ecommerce.R
import com.extremeSolution.ecommerce.app.extensions.makeInVisible
import com.extremeSolution.ecommerce.app.extensions.makeVisible
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.categories.CategoriesAdapter
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.products.ProductsAdapter
import com.extremeSolution.ecommerce.app.uiState.ErrorType
import com.extremeSolution.ecommerce.app.uiState.UiState
import com.extremeSolution.ecommerce.databinding.FragmentHomeBinding
import com.extremeSolution.ecommerce.domain.models.product.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private val categoriesAdapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }
    private lateinit var productsAdapter: ProductsAdapter

    private var globalProductsList = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        productsAdapter = ProductsAdapter(findNavController())

        initUI()
        loadDataFromCacheWhenOnline()

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
                loadDataFromCacheWhenOnline()
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { searchProducts(it) }
                    return false
                }

            })
        }
    }

    private fun searchProducts(sQuery: String) {
        val searchList = mutableListOf<Product>()

        // running a for loop to compare elements.
        for (item in globalProductsList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.category.lowercase(Locale.getDefault())
                    .contains(sQuery.lowercase(Locale.getDefault()))
                || item.title.lowercase(Locale.getDefault())
                    .contains(sQuery.lowercase(Locale.getDefault()))
                || item.description.lowercase(Locale.getDefault())
                    .contains(sQuery.lowercase(Locale.getDefault()))
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                searchList.add(item)
            }
        }
        if (searchList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            showErrorProducts("No Data Found..")
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            productsAdapter.setData(searchList)
        }
        if (sQuery == "") {
            populateProductsRV(globalProductsList)
        }
    }

    private fun loadDataFromCacheWhenOnline() {
        showProductsLoading()
        lifecycleScope.launch {

            viewModel.productsCache.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    hideProductsLoading()
                    globalProductsList.clear()
                    globalProductsList.addAll(database)
                    populateProductsRV(database)
                } else {
                    getCategoriesAndProductsFromRemote()
                }
            }
        }
    }

    private fun loadDataFromCacheWhenOffline() {
        lifecycleScope.launch {
            viewModel.productsCache.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    hideProductsLoading()
                    populateProductsRV(database)
                } else {
                    showErrorProducts(getString(R.string.no_data_found))
                }
            }
        }
    }

    private fun getCategoriesAndProductsFromRemote() {
        showCategoriesLoading()
        showProductsLoading()

        viewModel.getCategoriesAndProductsSafeCall()

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

                    globalProductsList.clear()
                    globalProductsList.addAll(response.data!!)
                }

                is UiState.Error -> {
                    hideProductsLoading()

                    loadDataFromCacheWhenOffline()

                    val errorMessage = when (response.errorType) {
                        ErrorType.EXCEPTION -> response.message.toString()
                        ErrorType.UNKNOWN -> getString(R.string.unidentified_error)
                        ErrorType.API_ERROR -> getString(R.string.request_is_not_successful)
                    }

                    showErrorProducts(errorMessage)
                }
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
        binding.progressBarCategories.makeVisible()
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
        binding.progressBarProducts.makeVisible()
    }

    private fun hideProductsLoading() {
        binding.apply {
            progressBarProducts.makeInVisible()
            rvProducts.makeVisible()
            errorLayoutProducts.makeInVisible()
            if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false
        }
    }

    private fun populateProductsRV(data: List<Product>?) {
        data?.let {
            binding.apply {
                errorLayoutProducts.makeInVisible()
                rvProducts.makeVisible()
            }
            binding.rvProducts.apply {
                layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = productsAdapter
            }
            productsAdapter.setData(it)
        }
    }
}