package com.example.flowerdelivery.ui.orders

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.flowerdelivery.R
import com.example.flowerdelivery.data.entity.Order
import com.example.flowerdelivery.databinding.FragmentOrdersBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrdersFragment : Fragment(), SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener,
    OrderClickListener {

    private val ordersRecyclerViewAdapter by lazy { OrdersRecyclerViewAdapter(this) }
    private val ordersViewModel by viewModel<OrdersViewModel>()
    private lateinit var viewDataBinding: FragmentOrdersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_orders, container, false
        )
        viewDataBinding.lifecycleOwner = this

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.ordersRecyclerView.apply {
            adapter = ordersRecyclerViewAdapter
        }

        viewDataBinding.viewModel = ordersViewModel
        viewDataBinding.swipeContainer.setOnRefreshListener {
            ordersViewModel.getAllOrders(false)
        }
        ordersViewModel.getAllOrders()
        ordersViewModel.ordersList.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                ordersRecyclerViewAdapter.clearAll()
                ordersRecyclerViewAdapter.setOrders(it)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu);
        setupSearchView(menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.menu_search)
        searchItem.setOnActionExpandListener(this@OrdersFragment)
        (searchItem.actionView as SearchView).apply {
            setOnQueryTextListener(this@OrdersFragment)
            queryHint = context.getString(R.string.search_view_hint)
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        ordersRecyclerViewAdapter.filter.filter(newText)
        return false
    }

    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
        ordersViewModel.isSearching.value = true
        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        ordersViewModel.isSearching.value = false
        return true
    }

    override fun onItemClick(order: Order) {
        Toast.makeText(context?.applicationContext, order.name, Toast.LENGTH_LONG).show()
    }

}