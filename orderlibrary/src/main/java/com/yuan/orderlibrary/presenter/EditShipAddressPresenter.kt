package com.kotlin.order.presenter

import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.presenter.view.EditShipAddressView
import com.kotlin.order.service.ShipAddressService
import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import javax.inject.Inject

/*
    编辑收货人信息Presenter
 */
class EditShipAddressPresenter @Inject constructor() : BasePresenter<EditShipAddressView>() {

    @Inject
    lateinit var shipAddressService: ShipAddressService


    /*
        添加收货人信息
     */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String) {
        mView.showLoading()
        shipAddressService.addShipAddress(shipUserName,shipUserMobile,shipAddress).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                    mView.onAddShipAddressResult(t)
            }
        }, lifecycleProvider)

    }

    /*
        修改收货人信息
     */
    fun editShipAddress(address:ShipAddress) {
        mView.showLoading()
        shipAddressService.editShipAddress(address).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onEditShipAddressResult(t)
            }
        }, lifecycleProvider)

    }


}
