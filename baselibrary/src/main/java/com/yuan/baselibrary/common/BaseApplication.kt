package com.yuan.baselibrary.common

import android.app.Application
import android.content.Context
import com.yuan.baselibrary.injection.component.AppComponent
import com.yuan.baselibrary.injection.component.DaggerAppComponent
import com.yuan.baselibrary.injection.module.AppModule

open class BaseApplication: Application() {

     lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppInjection()
        context = this
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var context: Context
    }
}