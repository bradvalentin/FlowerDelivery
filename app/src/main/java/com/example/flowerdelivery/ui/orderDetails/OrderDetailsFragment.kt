package com.example.flowerdelivery.ui.orderDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionInflater
import com.example.flowerdelivery.R
import com.example.flowerdelivery.data.entity.Order
import com.example.flowerdelivery.databinding.FragmentOrderDetailsBinding
import java.util.concurrent.TimeUnit

const val TRANSITION_DURATION = 250L

class OrderDetailsFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentOrderDetailsBinding
    private lateinit var orderArgs: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val args = requireArguments()
        orderArgs = OrderDetailsFragmentArgs.fromBundle(args).order
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_order_details, container, false
        )
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.order = orderArgs

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition(TRANSITION_DURATION, TimeUnit.MILLISECONDS)

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.flowerImageView.transitionName = orderArgs.image_url
        viewDataBinding.costumerName.transitionName = orderArgs.contact.deliver_to
        viewDataBinding.orderItemName.transitionName = orderArgs.name
        viewDataBinding.distance.transitionName = orderArgs.distance.toString()

    }

}