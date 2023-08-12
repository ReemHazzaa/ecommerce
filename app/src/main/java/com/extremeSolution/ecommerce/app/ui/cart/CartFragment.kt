package com.extremeSolution.ecommerce.app.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.R
import com.extremeSolution.ecommerce.app.extensions.makeInVisible
import com.extremeSolution.ecommerce.app.extensions.makeVisible
import com.extremeSolution.ecommerce.app.extensions.showAppDialog
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.cart.CartAdapter
import com.extremeSolution.ecommerce.databinding.FragmentCartBinding
import com.extremeSolution.ecommerce.domain.models.product.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private val cartAdapter: CartAdapter by lazy { CartAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        initUI()
        readCart()

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initUI() {
        binding.apply {
            fabEmptyCart.setOnClickListener {
                requireActivity().showAppDialog(body = getString(R.string.delete_all_items_in_cart),
                    posClicked = { emptyCart() },
                    negClicked = {})
            }
        }
    }

    private fun readCart() {
        showLoading()
        viewModel.cart.observe(viewLifecycleOwner) { cart ->
            if (cart != null && cart.isNotEmpty()) {
                hideProductsLoading()
                populateCartRV(cart)
            } else {
                hideProductsLoading()
                showError(getString(R.string.empty_cart_msg))
            }
        }
    }

    private fun emptyCart() {
        viewModel.cart.observe(viewLifecycleOwner) { products ->
            if (products != null && products.isNotEmpty()) {
                viewModel.emptyCart(products)
            }
        }
    }

    private fun populateCartRV(cart: List<Product>?) {
        cart?.let {
            binding.apply {
                errorLayout.makeInVisible()
                rvCart.makeVisible()
            }
            binding.rvCart.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = cartAdapter
            }
            cartAdapter.setData(it)
            cartAdapter.setOnClickListener(object : CartAdapter.OnClickListener {
                override fun onClick(position: Int, product: Product) {
                    viewModel.removeProductFromCart(product)
                }
            })
        }
    }

    private fun showLoading() {
        binding.progressBarProducts.makeVisible()
    }

    private fun hideProductsLoading() {
        binding.apply {
            progressBarProducts.makeInVisible()
            rvCart.makeVisible()
            errorLayout.makeInVisible()
        }
    }

    private fun showError(error: String) {
        binding.apply {
            errorLayout.makeVisible()
            progressBarProducts.makeInVisible()
            rvCart.makeInVisible()
            tvError.text = error
        }
    }
}