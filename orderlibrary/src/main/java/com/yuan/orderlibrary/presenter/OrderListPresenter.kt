package com.kotlin.order.presenter

import com.kotlin.order.data.protocol.Order
import com.kotlin.order.presenter.view.OrderListView
import com.kotlin.order.service.OrderService
import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import javax.inject.Inject

/*
    订单列表Presenter
 */
class OrderListPresenter @Inject constructor() : BasePresenter<OrderListView>() {

    @Inject
    lateinit var orderService: OrderService

    /*
        根据订单状态获取订单列表
     */
    fun getOrderList(orderStatus: Int) {

        mView.showLoading()
        orderService.getOrderList(orderStatus).execute(object : BaseSubscriber<MutableList<Order>?>(mView) {
            override fun onNext(t: MutableList<Order>?) {
                    mView.onGetOrderListResult(t)
            }
        }, lifecycleProvider)

    }

    /*
        订单确认收货
     */
    fun confirmOrder(orderId: Int) {
        mView.showLoading()
        orderService.confirmOrder(orderId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onConfirmOrderResult(t)
            }
        }, lifecycleProvider)

    }

    /*
        取消订单
     */
    fun cancelOrder(orderId: Int) {
        mView.showLoading()
        orderService.cancelOrder(orderId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onCancelOrderResult(t)
            }
        }, lifecycleProvider)

    }



}
