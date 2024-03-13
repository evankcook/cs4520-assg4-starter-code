package com.cs4520.assignment4


class Util {
    companion object {
        fun createProductList(data : List<List<Any?>>): List<Product> {
            val productList =  data.map{
                when (it[1] as String) {
                    "Food" -> Product.FoodProduct(it[0] as String, it[2] as String?, it[3] as Int)
                    "Equipment" -> Product.EquipmentProduct(it[0] as String, it[2] as String?, it[3]
                            as Int)
                    else -> throw IllegalArgumentException("Found an invalid product type.")
                }
            }
            return productList
        }
    }

}