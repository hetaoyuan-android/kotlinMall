package com.yuan.user.presenter.view

import com.yuan.baselibrary.presenter.view.BaseView

interface ResetView: BaseView {
    fun onResetPwdResult(result: String)
}