package com.yuan.user.presenter

import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.user.presenter.view.RegisterView

class RegisterPresenter: BasePresenter<RegisterView>() {

    fun register(mobile: String, verifyCode: String) {
        /**
         * 业务逻辑
         */
        mView.onRegisterResult(true)
    }
}