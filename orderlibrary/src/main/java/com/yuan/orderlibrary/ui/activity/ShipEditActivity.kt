package com.yuan.orderlibrary.ui.activity

import android.os.Bundle
import com.kotlin.order.injection.component.DaggerOrderComponent
import com.kotlin.order.injection.component.DaggerShipAddressComponent
import com.kotlin.order.injection.module.OrderModule
import com.kotlin.order.injection.module.ShipAddressModule
import com.kotlin.order.presenter.EditShipAddressPresenter
import com.kotlin.order.presenter.view.EditShipAddressView
import com.yuan.baselibrary.ext.onClick
import com.yuan.baselibrary.ui.activity.BaseMvpActivity
import com.yuan.orderlibrary.R
import kotlinx.android.synthetic.main.activity_edit_address.*
import org.jetbrains.anko.toast

class ShipEditActivity : BaseMvpActivity<EditShipAddressPresenter>(), EditShipAddressView {


    override fun injectComponent() {
        DaggerShipAddressComponent.builder().activityComponent(activtyComponent).shipAddressModule(ShipAddressModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)
        initView()
    }

    private fun initView() {
        mSaveBtn.onClick {
            if(mShipNameEt.text.isNullOrEmpty()) {
                toast("名称不能为空")
                return@onClick
            }
            if(mShipAddressEt.text.isNullOrEmpty()) {
                toast("电话不能为空")
                return@onClick
            }
            if(mShipMobileEt.text.isNullOrEmpty()) {
                toast("地址不能为空")
                return@onClick
            }
            mPresenter.addShipAddress(mShipNameEt.text.toString(), mShipMobileEt.text.toString(),
                mShipAddressEt.text.toString())
        }
    }

    override fun onAddShipAddressResult(result: Boolean) {

    }

    override fun onEditShipAddressResult(result: Boolean) {
        toast("新增成功")
        finish()
    }
}
