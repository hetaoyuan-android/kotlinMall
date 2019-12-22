package com.kotlin.order.presenter.view

import com.kotlin.order.data.protocol.Order
import com.yuan.baselibrary.presenter.view.BaseView

/*
    订单确认页 视图回调
 */
interface OrderConfirmView : BaseView {

    //获取订单回调
    fun onGetOrderByIdResult(result:Order)
    //提交订单回调
    fun onSubmitOrderResult(result:Boolean)
}
