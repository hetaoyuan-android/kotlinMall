package com.kotlin.order.presenter.view

import com.kotlin.order.data.protocol.Order
import com.yuan.baselibrary.presenter.view.BaseView

/*
    订单详情页 视图回调
 */
interface OrderDetailView : BaseView {

    fun onGetOrderByIdResult(result: Order)
}
