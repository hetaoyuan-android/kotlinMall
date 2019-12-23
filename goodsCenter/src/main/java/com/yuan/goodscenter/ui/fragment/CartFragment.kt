package com.yuan.goodscenter.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.goods.event.CartAllCheckedEvent
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.goods.event.UpdateTotalPriceEvent
import com.kotlin.goods.injection.component.DaggerCartComponent
import com.kotlin.goods.injection.module.CartModule
import com.kotlin.goods.presenter.CartListPresenter
import com.kotlin.goods.presenter.view.CartListView
import com.kotlin.goods.ui.adapter.CartGoodsAdapter
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.fragment.BaseMvpFragment
import com.yuan.goodscenter.R
import com.yuan.provider.common.ProviderConstant
import com.yuan.provider.router.RouterPath
import kotlinx.android.synthetic.main.fragment_cart.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast

class CartFragment: BaseMvpFragment<CartListPresenter>(), CartListView {

    private lateinit var mAdapter: CartGoodsAdapter
    private var mTotalPrice:Long = 0

    override fun onSubmitCartListResult(result: Int) {
        Log.e("onSubmitCartListResult", result.toString())
        ARouter.getInstance().build(RouterPath.OrderLibrary.PATH_ORDER_CONFIRM)
            .withInt(ProviderConstant.KEY_ORDER_ID,result)
            .navigation()
    }
    override fun onDeleteCartListResult(result: Boolean) {
        toast("删除成功")
        refreshEditStatus()
        mAdapter.notifyDataSetChanged()
        loadData()
    }
    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mHeaderBar.getRightView().visibility = View.VISIBLE
            mAllCheckedCb.isChecked = false
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mHeaderBar.getRightView().visibility = View.GONE
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
        //本地存储并发送事件刷新UI
        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE,result?.size?:0)
        Bus.send(UpdateCartSizeEvent())
        //更新总价
        updateTotalPrice()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
        initObserve()
    }

    private fun loadData() {
        mMultiStateView.viewState
        mPresenter.getCartList()
    }

    private fun initView() {
        mCartGoodsRv.layoutManager = LinearLayoutManager(context)
        mAdapter = CartGoodsAdapter(act)
        mCartGoodsRv.adapter = mAdapter

        mHeaderBar.getRightView().onClick {
            refreshEditStatus()
        }

        //全选
        mAllCheckedCb.onClick {
            for (item in mAdapter.dataList) {
                item.isSelected = mAllCheckedCb.isChecked
            }
            mAdapter.notifyDataSetChanged()
            updateTotalPrice()
        }
        //删除按钮事件
        mDeleteBtn.onClick {
            val cartIdList: MutableList<Int> = arrayListOf()
            mAdapter.dataList.filter { it.isSelected }
                .mapTo(cartIdList) { it.id }
            if (cartIdList.size == 0) {
                toast("请选择需要删除的数据")
            }else {
                mPresenter.deleteCartList(cartIdList)
            }
        }
        //结算按钮事件
        mSettleAccountsBtn.onClick {
            val cartGoodsList: MutableList<CartGoods> = arrayListOf()
            mAdapter.dataList.filter { it.isSelected }
                .mapTo(cartGoodsList){it}
            if (cartGoodsList.size == 0) {
                toast("请选择需要提交的数据")
            }else {
                mPresenter.submitCart(cartGoodsList,mTotalPrice)
            }
        }
    }

    private fun refreshEditStatus() {
        val isEditStatus = getString(R.string.common_edit) == mHeaderBar.getRightText()
        if (isEditStatus) {
            mTotalPriceTv.visibility = View.GONE
            mSettleAccountsBtn.visibility = View.GONE
            mDeleteBtn.visibility = View.VISIBLE
            mHeaderBar.getRightView().text = getString(R.string.common_complete)
        } else {
            mTotalPriceTv.visibility = View.VISIBLE
            mSettleAccountsBtn.visibility = View.VISIBLE
            mDeleteBtn.visibility = View.GONE

            mHeaderBar.getRightView().text = getString(R.string.common_edit)
        }

    }

    private fun initObserve() {
        Bus.observe<CartAllCheckedEvent>().subscribe { t: CartAllCheckedEvent ->
            run {
                mAllCheckedCb.isChecked = t.isAllChecked
                updateTotalPrice()
            }
        }
            .registerInBus(this)

        Bus.observe<UpdateTotalPriceEvent>().subscribe {
            updateTotalPrice()
        }
            .registerInBus(this)

    }
    override fun injectComponent() {
        DaggerCartComponent.builder().activityComponent(activtyComponent).cartModule(CartModule()).build().inject(this)
        mPresenter.mView = this    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private fun updateTotalPrice() {
        mTotalPrice = mAdapter.dataList
            .filter { it.isSelected }
            .map { it.goodsCount * it.goodsPrice }
            .sum()
        mTotalPriceTv.text = "合计${YuanFenConverter.changeF2YWithUnit(mTotalPrice)}"
    }

    fun setBackVisible(isVisible: Boolean) {
        if (isVisible) {
            mHeaderBar.getLeftText().visibility = View.VISIBLE
        } else{
            mHeaderBar.getLeftText().visibility = View.GONE
        }
    }
}