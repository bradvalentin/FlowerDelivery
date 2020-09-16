package com.example.flowerdelivery.ui.orders

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
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

        ordersViewModel.getAllOrders()
        ordersViewModel.getOrdersList().observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                ordersRecyclerViewAdapter.clearAll()
                ordersRecyclerViewAdapter.setOrders(it)
            }
        })
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

        viewDataBinding.ordersRecyclerView.apply {
            adapter = ordersRecyclerViewAdapter
        }

        viewDataBinding.viewModel = ordersViewModel
        viewDataBinding.swipeContainer.setOnRefreshListener {
            ordersViewModel.getAllOrders(false)
        }

        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        viewDataBinding.ordersRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
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
        viewDataBinding.swipeContainer.isEnabled = false
        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        viewDataBinding.swipeContainer.isEnabled = true
        return true
    }

    override fun onItemClick(
        order: Order,
        imageView: ImageView,
        orderItemName: TextView,
        costumerName: TextView,
        distance: TextView
    ) {

        val direction: NavDirections =
            OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(order)

        val extras = FragmentNavigatorExtras(
            imageView to order.image_url,
            orderItemName to order.name,
            costumerName to order.contact.deliver_to,
            distance to order.distance.toString()
        )

        findNavController().navigate(direction, extras)

    }


}