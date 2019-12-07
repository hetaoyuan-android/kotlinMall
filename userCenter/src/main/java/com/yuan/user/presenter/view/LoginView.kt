package com.yuan.user.presenter.view

import com.yuan.baselibrary.presenter.view.BaseView
import com.yuan.user.data.protocol.UserInfo

interface LoginView: BaseView {
    fun onLoginResult(result: UserInfo)
}