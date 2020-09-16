package com.example.flowerdelivery

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable

import kotlin.collections.ArrayList


class OrdersRecyclerViewAdapter() : RecyclerView.Adapter<OrdersRecyclerViewAdapter.ViewHolder>(), Filterable {

    var ordersFilterList = ArrayList<Order>()
    var ordersList: List<Order> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = ordersList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                ordersFilterList = if (charSearch.isEmpty()) {
                    ArrayList(ordersList)
                } else {
                    val resultList = ArrayList<Order>()
                    for (row in ordersList) {
            //                        if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
            //                            resultList.add(row)
            //                        }
                    }
                    resultList
                }
                return FilterResults().apply {
                    values = ordersFilterList
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                ordersFilterList = results?.values as ArrayList<Order>
                notifyDataSetChanged()
            }

        }
    }
}