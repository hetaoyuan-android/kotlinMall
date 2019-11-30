package com.yuan.user.presenter.view

import com.yuan.baselibrary.presenter.view.BaseView

interface RegisterView: BaseView {
    fun onRegisterResult(result: Boolean)
}