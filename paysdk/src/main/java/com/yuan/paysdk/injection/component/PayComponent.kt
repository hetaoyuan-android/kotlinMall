package com.kotlin.pay.injection.component

import com.kotlin.pay.injection.module.PayModule
import com.yuan.baselibrary.injection.PreComponentScope
import com.yuan.baselibrary.injection.component.ActivityComponent
import com.yuan.paysdk.ui.activity.CashRegisterActivity
import dagger.Component

/*
    支付Component
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(PayModule::class))
interface PayComponent {
    fun inject(activity: CashRegisterActivity)
}
