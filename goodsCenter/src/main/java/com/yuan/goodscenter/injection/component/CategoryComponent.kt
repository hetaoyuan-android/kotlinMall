package com.kotlin.goods.injection.component

import com.kotlin.goods.injection.module.CategoryModule
import com.yuan.baselibrary.injection.PreComponentScope
import com.yuan.baselibrary.injection.component.ActivityComponent
import com.yuan.goodscenter.ui.fragment.CategoryFragment
import dagger.Component

/*
    商品分类Component
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(CategoryModule::class))
interface CategoryComponent {
    fun inject(fragment: CategoryFragment)
}
