package com.yuan.user.presenter

import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import com.yuan.user.presenter.view.RegisterView
import com.yuan.user.services.UserServices
import com.yuan.user.services.impl.UserServicesImpl
import javax.inject.Inject

class RegisterPresenter @Inject constructor() : BasePresenter<RegisterView>() {

    @Inject
    lateinit var userServices: UserServices

    /**
     * 注册
     */
    fun register(mobile: String, verifyCode: String, pwd: String) {
        /**
         * 业务逻辑
         */
        userServices.register(mobile, verifyCode, pwd)
            .execute(object : BaseSubscriber<Boolean>() {
                override fun onNext(t: Boolean) {
                    super.onNext(t)
                    mView.onRegisterResult(t)
                }
            })
    }

    /**
     * 登录
     */
}