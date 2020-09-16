package com.example.flowerdelivery

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : Fragment(), SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private val ordersRecyclerViewAdapter by lazy { OrdersRecyclerViewAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ordersRecyclerView.apply {
            adapter = ordersRecyclerViewAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu);
        setupSearchView(menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.menu_search)
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
        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        return true
    }

}