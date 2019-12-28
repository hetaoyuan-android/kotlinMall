package com.kotlin.order.injection.component

import com.kotlin.order.injection.module.OrderModule
import com.kotlin.order.ui.activity.OrderDetailActivity
import com.kotlin.order.ui.fragment.OrderFragment
import com.yuan.baselibrary.injection.PreComponentScope
import com.yuan.baselibrary.injection.component.ActivityComponent
import com.yuan.orderlibrary.ui.activity.OrderConfirmActivity
import dagger.Component

/*
    订单Component
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(OrderModule::class))
interface OrderComponent {
    fun inject(activity: OrderConfirmActivity)
    fun inject(fragment: OrderFragment)
//
    fun inject(activity: OrderDetailActivity)
}
