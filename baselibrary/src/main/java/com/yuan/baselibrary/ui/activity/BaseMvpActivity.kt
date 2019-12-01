package com.yuan.baselibrary.ui.activity

import android.os.Bundle
import com.yuan.baselibrary.common.BaseApplication
import com.yuan.baselibrary.injection.component.ActivityComponent
import com.yuan.baselibrary.injection.component.DaggerActivityComponent
import com.yuan.baselibrary.injection.module.ActivityModule
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.presenter.view.BaseView
import javax.inject.Inject

open class BaseMvpActivity<T:BasePresenter<*>>:BaseActivity(), BaseView {
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError() {
    }

    @Inject
    lateinit var mPresenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
    }

    lateinit var activtyComponent: ActivityComponent

    private fun initActivityInjection() {
        activtyComponent = DaggerActivityComponent.builder().appComponent((application as BaseApplication).appComponent)
            .activityModule(ActivityModule(this)).build()
    }
}