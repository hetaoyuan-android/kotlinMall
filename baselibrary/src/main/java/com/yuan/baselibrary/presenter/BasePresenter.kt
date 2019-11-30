package com.yuan.baselibrary.presenter

import com.yuan.baselibrary.presenter.view.BaseView

open class BasePresenter<T:BaseView> {
    lateinit var mView:T
}