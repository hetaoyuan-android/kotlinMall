package com.kotlin.goods.presenter.view

import com.kotlin.goods.data.protocol.Category
import com.yuan.baselibrary.presenter.view.BaseView


/*
    商品分类 视图回调
 */
interface CategoryView : BaseView {

    //获取商品分类列表
    fun onGetCategoryResult(result: MutableList<Category>?)
}
