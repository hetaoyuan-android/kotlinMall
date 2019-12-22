package com.yuan.orderlibrary.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.injection.component.DaggerOrderComponent
import com.kotlin.order.injection.module.OrderModule
import com.kotlin.order.presenter.OrderConfirmPresenter
import com.kotlin.order.presenter.view.OrderConfirmView
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.orderlibrary.R
import com.yuan.provider.common.ProviderConstant
import com.yuan.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_confirm.*

@Route(path = "/orderlibrary/confirm")
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView {

    private var mOrderId: Int = 0

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(activtyComponent).orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)
        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1)
        mPresenter.getOrderById(mOrderId)
    }

    override fun onGetOrderByIdResult(result: Order) {
        mTotalPriceTv.text = "合计:${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"
    }

    override fun onSubmitOrderResult(result: Boolean) {
    }


}
