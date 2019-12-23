package com.yuan.orderlibrary.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.injection.component.DaggerOrderComponent
import com.kotlin.order.injection.module.OrderModule
import com.kotlin.order.presenter.OrderConfirmPresenter
import com.kotlin.order.presenter.view.OrderConfirmView
import com.kotlin.order.ui.adapter.OrderGoodsAdapter
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.orderlibrary.R
import com.yuan.provider.common.ProviderConstant
import com.yuan.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

@Route(path = RouterPath.OrderLibrary.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView {

    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId: Int = 0
    private lateinit var mAdapter: OrderGoodsAdapter

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(activtyComponent).orderModule(OrderModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)
        initView()
        loadData()

    }


    private fun initView() {
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(act)
        mOrderGoodsRv.adapter = mAdapter
        mSelectShipTv.onClick {
            startActivity<ShipAddressActivity>()
        }
    }

    private fun loadData() {
        mPresenter.getOrderById(mOrderId)

    }


    override fun onGetOrderByIdResult(result: Order) {
        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "合计:${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"
    }

    override fun onSubmitOrderResult(result: Boolean) {
    }


}
