package com.kotlin.goods.injection.component

import com.kotlin.goods.injection.module.CartModule
import com.yuan.baselibrary.injection.PreComponentScope
import com.yuan.baselibrary.injection.component.ActivityComponent
import com.yuan.goodscenter.ui.fragment.CartFragment
import dagger.Component

/*
    购物车Component
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(CartModule::class))
interface CartComponent {
    fun inject(fragment: CartFragment)
}
