package com.yuan.orderlibrary.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.event.SelectAddressEvent
import com.kotlin.order.injection.component.DaggerShipAddressComponent
import com.kotlin.order.injection.module.ShipAddressModule
import com.kotlin.order.presenter.ShipAddressPresenter
import com.kotlin.order.presenter.view.ShipAddressView
import com.kotlin.order.ui.adapter.ShipAddressAdapter
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.orderlibrary.R
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ShipAddressActivity : BaseMvpActivity<ShipAddressPresenter>(), ShipAddressView {


    private lateinit var mAdapter: ShipAddressAdapter

    override fun injectComponent() {
        DaggerShipAddressComponent.builder().activityComponent(activtyComponent).shipAddressModule(ShipAddressModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        initView()
    }

    private fun initView() {

        mAddressRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ShipAddressAdapter(act)
        mAddressRv.adapter = mAdapter

        mAdapter.mOptClickListener = object: ShipAddressAdapter.OnOptClickListener {
            override fun onSetDefault(address: ShipAddress) {
                mPresenter.setDefaultShipAddress(address)
            }

            override fun onEdit(address: ShipAddress) {
                startActivity<ShipEditActivity>(OrderConstant.KEY_SHIP_ADDRESS to address)
            }

            override fun onDelete(address: ShipAddress) {
                AlertView("删除", "确定删除该地址？", "取消", null, arrayOf("确定"), this@ShipAddressActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
                    if (position == 0){
                        mPresenter.deleteShipAddress(address.id)
                    }
                }

                ).show()
            }
        }

        //单项点击事件
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress>{
            override fun onItemClick(item: ShipAddress, position: Int) {
                Bus.send(SelectAddressEvent(item))
                finish()
            }
        })

        mAddAddressBtn.onClick {
            startActivity<ShipEditActivity>()
        }
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        mMultiStateView.viewState
        mPresenter.getShipAddressList()
        mAdapter.notifyDataSetChanged()
    }

    override fun onGetShipAddressResult(result: MutableList<ShipAddress>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onSetDefaultResult(result: Boolean) {
        toast("设置默认成功")
        loadData()
    }

    override fun onDeleteResult(result: Boolean) {
        toast("删除成功")
        loadData()
    }
}
