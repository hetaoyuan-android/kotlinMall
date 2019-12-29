package com.kotlin.pay.presenter

import com.kotlin.pay.presenter.view.PayView
import com.kotlin.pay.service.PayService
import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import javax.inject.Inject

/*
    支付Presenter
 */
class PayPresenter @Inject constructor() : BasePresenter<PayView>() {
    @Inject
    lateinit var service: PayService

    /*
        获取支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long) {
        mView.showLoading()
        service.getPaySign(orderId,totalPrice).execute(object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetSignResult(t)
            }
        }, lifecycleProvider)

    }

    /*
        订单支付，同步订单状态
     */
    fun payOrder(orderId: Int) {
        mView.showLoading()
        service.payOrder(orderId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onPayOrderResult(t)
            }
        }, lifecycleProvider)

    }



}
