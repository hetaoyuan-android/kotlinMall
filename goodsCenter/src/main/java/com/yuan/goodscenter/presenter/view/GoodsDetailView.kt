package com.kotlin.goods.presenter.view

import com.kotlin.goods.data.protocol.Goods
import com.yuan.baselibrary.presenter.view.BaseView

/*
    商品详情 视图回调
 */
interface GoodsDetailView : BaseView {

    //获取商品详情
    fun onGetGoodsDetailResult(result: Goods)
    //加入购物车
    fun onAddCartResult(result: Int)
}
