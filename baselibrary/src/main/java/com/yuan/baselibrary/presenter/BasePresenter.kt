package com.yuan.baselibrary.presenter

import com.trello.rxlifecycle.LifecycleProvider
import com.yuan.baselibrary.presenter.view.BaseView
import javax.inject.Inject

open class BasePresenter<T:BaseView> {
    lateinit var mView:T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>
}