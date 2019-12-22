package com.yuan.baselibrary.common

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.yuan.baselibrary.injection.component.AppComponent
import com.yuan.baselibrary.injection.component.DaggerAppComponent
import com.yuan.baselibrary.injection.module.AppModule

open class BaseApplication: Application() {

     lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppInjection()
        context = this

        //ARouter初始化
        ARouter.openLog()    // 打印日志
        ARouter.openDebug()
        ARouter.init(this)
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var context: Context
    }
}