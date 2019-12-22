package com.kotlin.order.injection.component

import com.kotlin.order.injection.module.ShipAddressModule
import com.yuan.baselibrary.injection.PreComponentScope
import com.yuan.baselibrary.injection.component.ActivityComponent
import dagger.Component

/*
    收货人信息Component
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(ShipAddressModule::class))
interface ShipAddressComponent {
//    fun inject(activity: ShipAddressEditActivity)
//    fun inject(activity: ShipAddressActivity)
}
