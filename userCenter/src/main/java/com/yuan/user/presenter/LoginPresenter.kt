package com.yuan.user.presenter

import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import com.yuan.user.data.protocol.UserInfo
import com.yuan.user.presenter.view.LoginView
import com.yuan.user.services.UserServices
import javax.inject.Inject

class LoginPresenter @Inject constructor() : BasePresenter<LoginView>() {

    @Inject
    lateinit var userServices: UserServices
    /**
     * 注册
     */
    fun login(mobile: String, pwd: String,  pushId: String) {
        mView.showLoading()
        userServices.login(mobile, pushId, pwd)
            .execute(object : BaseSubscriber<UserInfo>(mView) {
                override fun onNext(t: UserInfo) {
                    mView.onLoginResult(t)
                }
            },lifecycleProvider)
    }

    /**
     * 登录
     */
}