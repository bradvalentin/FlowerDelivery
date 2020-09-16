package com.example.flowerdelivery.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.flowerdelivery.R
import com.example.flowerdelivery.data.entity.Order
import com.example.flowerdelivery.databinding.OrderListItemBinding
import java.util.*
import kotlin.collections.ArrayList


class OrdersRecyclerViewAdapter(private val orderClickListener: OrderClickListener) : RecyclerView.Adapter<OrdersRecyclerViewAdapter.OrderViewHolder>(), Filterable {

    var ordersList: ArrayList<Order> = ArrayList()
    private var ordersListAll: ArrayList<Order> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {

        val viewBinding: OrderListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.order_list_item, parent, false
        )
        return OrderViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.view.apply {
            order = ordersList[position]
            orderClickInterface = orderClickListener
        }
    }

    fun clearAll() {
        ordersList.clear()
        notifyDataSetChanged()
    }

    fun setOrders(orders: ArrayList<Order>) {
        ordersList = ArrayList(orders)
        ordersListAll = ArrayList(orders)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = ordersList.size

    inner class OrderViewHolder(val view: OrderListItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val ordersFilterList = ArrayList<Order>()

                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    ordersFilterList.addAll(ordersListAll)
                } else {
                    for (row in ordersListAll) {
                        if (row.contact.deliver_to.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            ordersFilterList.add(row)
                        }
                    }
                }
                return FilterResults().apply {
                    values = ordersFilterList
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                ordersList.clear()
                ordersList.addAll(results?.values as ArrayList<Order>)
                notifyDataSetChanged()
            }

        }
    }
}