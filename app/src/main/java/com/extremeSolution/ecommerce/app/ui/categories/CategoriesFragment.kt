package com.extremeSolution.ecommerce.app.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.extremeSolution.ecommerce.R
import com.extremeSolution.ecommerce.app.extensions.makeInVisible
import com.extremeSolution.ecommerce.app.extensions.makeVisible
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.categories.CategoriesAdapter
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.categoryProducts.productsElectronics.ProductsElectronicsAdapter
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.categoryProducts.productsJewelery.ProductsJeweleryAdapter
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.categoryProducts.productsMen.ProductsMenAdapter
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.categoryProducts.productsWomen.ProductsWomenAdapter
import com.extremeSolution.ecommerce.app.uiState.ErrorType
import com.extremeSolution.ecommerce.app.uiState.UiState
import com.extremeSolution.ecommerce.databinding.FragmentCategoriesBinding
import com.extremeSolution.ecommerce.domain.models.product.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val categoriesAdapter: CategoriesAdapter by lazy {
        CategoriesAdapter()
    }
    private val productsMenAdapter: ProductsMenAdapter by lazy {
        ProductsMenAdapter()
    }
    private val productsWomenAdapter: ProductsWomenAdapter by lazy {
        ProductsWomenAdapter()
    }
    private val productsJeweleryAdapter: ProductsJeweleryAdapter by lazy {
        ProductsJeweleryAdapter()
    }
    private val productsElectronicsAdapter: ProductsElectronicsAdapter by lazy {
        ProductsElectronicsAdapter()
    }
    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val view = binding.root

        initUI()
        getCategoriesThenProductsInEachOne()

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
                getCategoriesThenProductsInEachOne()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun getCategoriesThenProductsInEachOne() {
        showCategoriesLoading()
        showMenLoading()
        showWomenLoading()
        showJeweleryLoading()
        showElectronicsLoading()

        viewModel.safeGetCategoriesCall()

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

        viewModel.productsMenResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Loading -> showMenLoading()
                is UiState.Success -> {
                    hideMenLoading()
                    populateMenRV(response.data)
                }

                is UiState.Error -> {
                    hideMenLoading()

                    val errorMessage = when (response.errorType) {
                        ErrorType.EXCEPTION -> response.message.toString()
                        ErrorType.UNKNOWN -> getString(R.string.unidentified_error)
                        ErrorType.API_ERROR -> getString(R.string.request_is_not_successful)
                    }

                    showErrorMen(errorMessage)
                }
            }
        }

        viewModel.productsWomenResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Loading -> showWomenLoading()
                is UiState.Success -> {
                    hideWomenLoading()
                    populateWomenRV(response.data)
                }

                is UiState.Error -> {
                    hideWomenLoading()

                    val errorMessage = when (response.errorType) {
                        ErrorType.EXCEPTION -> response.message.toString()
                        ErrorType.UNKNOWN -> getString(R.string.unidentified_error)
                        ErrorType.API_ERROR -> getString(R.string.request_is_not_successful)
                    }

                    showErrorWomen(errorMessage)
                }
            }
        }

        viewModel.productsJewelResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Loading -> showJeweleryLoading()
                is UiState.Success -> {
                    hideJeweleryLoading()
                    populateJeweleryRV(response.data)
                }

                is UiState.Error -> {
                    hideJeweleryLoading()

                    val errorMessage = when (response.errorType) {
                        ErrorType.EXCEPTION -> response.message.toString()
                        ErrorType.UNKNOWN -> getString(R.string.unidentified_error)
                        ErrorType.API_ERROR -> getString(R.string.request_is_not_successful)
                    }

                    showErrorJewelery(errorMessage)
                }
            }
        }

        viewModel.productsElectResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Loading -> showElectronicsLoading()
                is UiState.Success -> {
                    hideElectronicsLoading()
                    populateElectronicsRV(response.data)
                }

                is UiState.Error -> {
                    hideElectronicsLoading()

                    val errorMessage = when (response.errorType) {
                        ErrorType.EXCEPTION -> response.message.toString()
                        ErrorType.UNKNOWN -> getString(R.string.unidentified_error)
                        ErrorType.API_ERROR -> getString(R.string.request_is_not_successful)
                    }

                    showErrorElectronics(errorMessage)
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

    // POPULATE RVs
    private fun populateMenRV(data: List<Product>?) {
        data?.let {
            productsMenAdapter.setData(it)
            binding.rvProductsMen.adapter = productsMenAdapter
        }
    }

    private fun populateWomenRV(data: List<Product>?) {
        data?.let {
            productsWomenAdapter.setData(it)
            binding.rvProductsWomen.adapter = productsWomenAdapter
        }
    }

    private fun populateJeweleryRV(data: List<Product>?) {
        data?.let {
            productsJeweleryAdapter.setData(it)
            binding.rvProductsJewelery.adapter = productsJeweleryAdapter
        }
    }

    private fun populateElectronicsRV(data: List<Product>?) {
        data?.let {
            productsElectronicsAdapter.setData(it)
            binding.rvProductsElectronics.adapter = productsElectronicsAdapter
        }
    }

    // SHOW LOADING
    private fun showCategoriesLoading() {
        binding.progressBarCategories.makeVisible()
    }

    private fun showMenLoading() {
        binding.progressBarProductsMen.makeVisible()
    }

    private fun showWomenLoading() {
        binding.progressBarProductsWomen.makeVisible()
    }

    private fun showJeweleryLoading() {
        binding.progressBarProductsJewelery.makeVisible()
    }

    private fun showElectronicsLoading() {
        binding.progressBarProductsElectronics.makeVisible()
    }

    // HIDE LOADING
    private fun hideCategoriesLoading() {
        binding.apply {
            progressBarCategories.makeInVisible()
            rvCategories.makeVisible()
            errorTvCategories.makeInVisible()
        }
    }

    private fun hideMenLoading() {
        binding.apply {
            progressBarProductsMen.makeInVisible()
            rvProductsMen.makeVisible()
            errorTvMen.makeInVisible()
        }
    }

    private fun hideWomenLoading() {
        binding.apply {
            progressBarProductsWomen.makeInVisible()
            rvProductsWomen.makeVisible()
            errorTvWomen.makeInVisible()
        }
    }

    private fun hideJeweleryLoading() {
        binding.apply {
            progressBarProductsJewelery.makeInVisible()
            rvProductsJewelery.makeVisible()
            errorTvJewelery.makeInVisible()
        }
    }

    private fun hideElectronicsLoading() {
        binding.apply {
            progressBarProductsElectronics.makeInVisible()
            rvProductsElectronics.makeVisible()
            errorTvElectronics.makeInVisible()
        }
    }

    // ERROR
    private fun showErrorCategories(error: String) {
        binding.apply {
            errorTvCategories.makeVisible()
            progressBarCategories.makeInVisible()
            rvCategories.makeInVisible()
            errorTvCategories.text = error
        }
    }

    private fun showErrorMen(error: String) {
        binding.apply {
            errorTvMen.makeVisible()
            progressBarProductsMen.makeInVisible()
            rvProductsMen.makeInVisible()
            errorTvMen.text = error
        }
    }

    private fun showErrorWomen(error: String) {
        binding.apply {
            errorTvWomen.makeVisible()
            progressBarProductsWomen.makeInVisible()
            rvProductsWomen.makeInVisible()
            errorTvWomen.text = error
        }
    }

    private fun showErrorJewelery(error: String) {
        binding.apply {
            errorTvJewelery.makeVisible()
            progressBarProductsJewelery.makeInVisible()
            rvProductsJewelery.makeInVisible()
            errorTvJewelery.text = error
        }
    }

    private fun showErrorElectronics(error: String) {
        binding.apply {
            errorTvElectronics.makeVisible()
            progressBarProductsElectronics.makeInVisible()
            rvProductsElectronics.makeInVisible()
            errorTvElectronics.text = error
        }
    }


}