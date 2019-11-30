package com.yuan.baselibrary.ui.activity

import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.presenter.view.BaseView

open class BaseMvpActivity<T:BasePresenter<*>>:BaseActivity(), BaseView {
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError() {
    }

    lateinit var mPresenter: T
}