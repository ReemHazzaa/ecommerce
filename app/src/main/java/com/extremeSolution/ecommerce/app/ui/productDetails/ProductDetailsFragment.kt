package com.extremeSolution.ecommerce.app.ui.productDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.extremeSolution.ecommerce.R
import com.extremeSolution.ecommerce.app.extensions.makeInVisible
import com.extremeSolution.ecommerce.app.extensions.makeVisible
import com.extremeSolution.ecommerce.app.extensions.showSnackBar
import com.extremeSolution.ecommerce.app.uiState.ErrorType
import com.extremeSolution.ecommerce.app.uiState.UiState
import com.extremeSolution.ecommerce.databinding.FragmentProductDetailsBinding
import com.extremeSolution.ecommerce.domain.models.product.Product
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private var inCartId: Int = 0
    private lateinit var globalProduct: Product
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductDetailsViewModel by viewModels()
    private val args: ProductDetailsFragmentArgs by navArgs()

    private var productInCart = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        initUI()
        loadDataFromCacheWhenOnline()
        checkInCartItem(binding.btAddToCart)

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

            btAddToCart.setOnClickListener {
                if (::globalProduct.isInitialized) {
                    if (productInCart) {
                        removeFromCart()
                    } else {
                        saveToCart()
                    }
                }
            }
        }
    }

    private fun loadDataFromCacheWhenOnline() {
        showLoading()

        lifecycleScope.launch {
            viewModel.readProductCached(args.productId).observe(viewLifecycleOwner) { product ->
                if (product?.title != null) {
                    hideLoading()
                    populateUI(product)
                    globalProduct = product
                } else {
                    getDetails()
                }
            }
        }
    }

    private fun loadDataFromCacheWhenOffline() {
        lifecycleScope.launch {
            viewModel.readProductCached(args.productId).observe(viewLifecycleOwner) { product ->
                if (product?.title != null) {
                    hideLoading()
                    populateUI(product)
                    globalProduct = product
                } else {
                    showError(getString(R.string.no_data_found))
                }

            }
        }
    }

    private fun getDetails() {
        showLoading()
        viewModel.getProductDetailsSafeCall(args.productId)
        viewModel.productsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    populateUI(response.data)
                    globalProduct = response.data!!
                }

                is UiState.Error -> {
                    hideLoading()
                    loadDataFromCacheWhenOffline()

                    val errorMessage = when (response.errorType) {
                        ErrorType.EXCEPTION -> response.message.toString()
                        ErrorType.UNKNOWN -> getString(R.string.unidentified_error)
                        ErrorType.API_ERROR -> getString(R.string.request_is_not_successful)
                    }

                    showError(errorMessage)
                }
            }
        }
    }

    private fun populateUI(data: Product?) {
        binding.btAddToCart.isEnabled = true
        val imageList = ArrayList<SlideModel>()


        data?.let {
            imageList.add(SlideModel(data.image, data.title + "1", ScaleTypes.CENTER_INSIDE))
            imageList.add(SlideModel(data.image, data.title + "2", ScaleTypes.CENTER_INSIDE))

            binding.apply {
                tvTitle.text = data.title
                tvDescription.text = data.description
                tvPrice.text = data.price.toString()
                tvCount.text = data.rating.count.toString()
                tvVoteAvg.text = data.rating.rate.toString()
                imageSlider.setImageList(imageList)
            }
        }
    }

    private fun showError(error: String) {
        binding.apply {
            errorLayout.makeVisible()
            progressBar.makeInVisible()
            nestedScrollView.makeInVisible()
            imageSlider.makeInVisible()
            errorTvProducts.text = error
            btAddToCart.isEnabled = false
        }
    }

    private fun showLoading() {
        binding.progressBar.makeVisible()
        if (binding.swipeRefresh.isRefreshing) binding.swipeRefresh.isRefreshing = false
    }

    private fun hideLoading() {
        binding.apply {
            progressBar.makeInVisible()
            nestedScrollView.makeVisible()
            imageSlider.makeVisible()
            errorLayout.makeInVisible()
        }
    }

    private fun checkInCartItem(button: MaterialButton) {
        viewModel.cart.observe(viewLifecycleOwner) { products ->
            try {
                val product = products.find { it.id == globalProduct.id }
                if (product != null) {   // is in cart
                    changeAddToCartButtonState(
                        button,
                        getString(R.string.in_cart),
                    )
                    inCartId = product.id
                    productInCart = true
                } else {
                    changeAddToCartButtonState(
                        button,
                        getString(R.string.add_to_cart),
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveToCart() {
        viewModel.addProductToCart(globalProduct)
        changeAddToCartButtonState(
            binding.btAddToCart,
            getString(R.string.in_cart)
        )
        activity?.showSnackBar(getString(R.string.added_to_cart))
        productInCart = true
        inCartId = globalProduct.id

    }

    private fun removeFromCart() {
        viewModel.removeProductFromCart(globalProduct.id)
        changeAddToCartButtonState(
            binding.btAddToCart,
            getString(R.string.add_to_cart),
        )
        activity?.showSnackBar(getString(R.string.removed_from_cart))
        productInCart = false
    }


    private fun changeAddToCartButtonState(
        button: MaterialButton,
        buttonTxt: String,
    ) {
        button.text = buttonTxt
    }
}