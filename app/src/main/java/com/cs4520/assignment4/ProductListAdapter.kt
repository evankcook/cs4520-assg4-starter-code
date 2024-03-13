package com.cs4520.assignment4

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.databinding.ProductItemBinding


class ProductListAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    class ProductViewHolder(binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val productImageView: ImageView = binding.productImage
        private val productNameView: TextView = binding.productName
        private val productExpiryView: TextView = binding.productExpiry
        private val productPriceView: TextView = binding.productPrice

        fun bind(product: Product) {
            productNameView.text = product.name
            productPriceView.text = itemView.context.getString(R.string.currency,
                product.price.toString())

            if (product.expiryDate.isNullOrEmpty()) {
                productExpiryView.visibility = View.GONE
            } else {
                productExpiryView.visibility = View.VISIBLE
                productExpiryView.text = product.expiryDate
            }

            val backgroundColor = when (product) {
                is Product.EquipmentProudct -> "#E06666"
                is Product.FoodProduct -> "#FFD965"
            }
            itemView.setBackgroundColor(Color.parseColor(backgroundColor))

            val image = when (product) {
                is Product.EquipmentProduct -> R.drawable.equipment
                is Product.FoodProduct -> R.drawable.food
            }
            productImageView.setImageResource(image)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(view, parent, false)

        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }
}