package com.yuan.user.presenter

import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import com.yuan.user.presenter.view.RegisterView
import com.yuan.user.services.UserServices
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
//        if (!checkNetWork()) {
//            Log.e("NetWork","网络不可用")
//            return
//        }
        mView.showLoading()
        userServices.register(mobile, verifyCode, pwd)
            .execute(object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    if(t)
                    mView.onRegisterResult("注册成功")
                }
            },lifecycleProvider)
    }

    /**
     * 登录
     */
}