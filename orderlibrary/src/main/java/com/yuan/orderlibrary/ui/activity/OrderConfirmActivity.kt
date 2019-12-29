package com.yuan.orderlibrary.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.event.SelectAddressEvent
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
import org.jetbrains.anko.toast

@Route(path = RouterPath.OrderLibrary.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView {

    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId: Int = 0
    private var mCurrentOrder: Order? = null
    private lateinit var mAdapter: OrderGoodsAdapter

    override fun injectComponent() {
        DaggerOrderComponent.builder().activityComponent(activtyComponent).orderModule(OrderModule()).build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)
        initView()
        initObserve()
        loadData()

    }

    /*
       初始化选择收货人事件监听
    */
    private fun initObserve() {
        Bus.observe<SelectAddressEvent>()
            .subscribe { t: SelectAddressEvent ->
                run {
                    mCurrentOrder?.let {
                        it.shipAddress = t.address
                    }
                    updateAddressView()
                }
            }
            .registerInBus(this)

    }

    /*
 根据是否有收货人信息，更新视图
*/
    private fun updateAddressView() {
        mCurrentOrder?.let {
            if (it.shipAddress == null) {
                mSelectShipTv.visibility = View.VISIBLE
                mShipView.visibility = View.GONE
            } else {
                mSelectShipTv.visibility = View.GONE
                mShipView.visibility = View.VISIBLE

                mShipNameTv.text = it.shipAddress!!.shipUserName + "  " +
                        it.shipAddress!!.shipUserMobile
                mShipAddressTv.text = it.shipAddress!!.shipAddress
            }
        }
    }

    private fun initView() {

        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(act)
        mOrderGoodsRv.adapter = mAdapter
        mSelectShipTv.onClick {
            startActivity<ShipAddressActivity>()
        }
        mShipView.onClick {
            startActivity<ShipAddressActivity>()
        }
        mSubmitOrderBtn.onClick {
            mCurrentOrder?.let {
                mPresenter.submitOrder(it)
            }
        }
    }

    private fun loadData() {
        mPresenter.getOrderById(mOrderId)

    }


    override fun onGetOrderByIdResult(result: Order) {
        mCurrentOrder = result
        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "合计:${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"
        updateAddressView()
    }

    override fun onSubmitOrderResult(result: Boolean) {
        toast("订单提交成功")
        ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
            .withInt(ProviderConstant.KEY_ORDER_ID,mCurrentOrder!!.id)
            .withLong(ProviderConstant.KEY_ORDER_PRICE,mCurrentOrder!!.totalPrice)
            .navigation()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }


}
