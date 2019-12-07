package com.yuan.baselibrary.ui.activity

import android.os.Bundle
import com.kotlin.base.utils.NetWorkUtils
import com.yuan.baselibrary.common.BaseApplication
import com.yuan.baselibrary.injection.component.ActivityComponent
import com.yuan.baselibrary.injection.component.AppComponent
import com.yuan.baselibrary.injection.component.DaggerActivityComponent
import com.yuan.baselibrary.injection.component.DaggerAppComponent
import com.yuan.baselibrary.injection.module.ActivityModule
import com.yuan.baselibrary.injection.module.AppModule
import com.yuan.baselibrary.injection.module.LifecycleProviderModule
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.presenter.view.BaseView
import com.yuan.baselibrary.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

open abstract class BaseMvpActivity<T:BasePresenter<*>>:BaseActivity(), BaseView {
    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(text: String) {
        toast(text)
    }

    @Inject
    lateinit var mPresenter: T

    private lateinit var mLoadingDialog: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
        mLoadingDialog = ProgressLoading.create(this)
    }

    abstract fun injectComponent()

    lateinit var activtyComponent: ActivityComponent
    lateinit var appComponent: AppComponent

    private fun initActivityInjection() {
        var application = BaseApplication()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(application)).build()
        activtyComponent = DaggerActivityComponent.builder().appComponent(appComponent)
            .activityModule(ActivityModule(this)).lifecycleProviderModule(LifecycleProviderModule(this))
            .build()
    }

        fun checkNetWork(): Boolean {
        return NetWorkUtils.isNetWorkAvailable(this)
    }
}