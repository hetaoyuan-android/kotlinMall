package com.yuan.baselibrary.common

import android.app.Application
import com.yuan.baselibrary.injection.component.AppComponent
import com.yuan.baselibrary.injection.component.DaggerAppComponent
import com.yuan.baselibrary.injection.module.AppModule

open class BaseApplication: Application() {

     lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppInjection()
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}