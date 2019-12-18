package com.kotlin.goods.injection.component

import com.kotlin.goods.injection.module.GoodsModule
import com.yuan.baselibrary.injection.PreComponentScope
import com.yuan.baselibrary.injection.component.ActivityComponent
import com.yuan.goodscenter.ui.adtivity.GoodsActivity
import dagger.Component

/*
    商品Component
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(GoodsModule::class))
interface GoodsComponent {
    fun inject(activity: GoodsActivity)
//    fun inject(fragment: GoodsDetailTabOneFragment)
}
