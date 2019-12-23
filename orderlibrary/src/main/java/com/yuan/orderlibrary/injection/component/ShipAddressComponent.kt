package com.kotlin.order.injection.component

import com.kotlin.order.injection.module.ShipAddressModule
import com.yuan.baselibrary.injection.PreComponentScope
import com.yuan.baselibrary.injection.component.ActivityComponent
import com.yuan.orderlibrary.ui.activity.ShipAddressActivity
import com.yuan.orderlibrary.ui.activity.ShipEditActivity
import dagger.Component

/*
    收货人信息Component
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(ShipAddressModule::class))
interface ShipAddressComponent {
    fun inject(activity: ShipEditActivity)
    fun inject(activity: ShipAddressActivity)
}
