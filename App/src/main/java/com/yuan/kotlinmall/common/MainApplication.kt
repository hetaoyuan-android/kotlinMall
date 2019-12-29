package com.yuan.kotlinmall.common

import cn.jpush.android.api.JPushInterface
import com.yuan.baselibrary.common.BaseApplication

class MainApplication :BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)

    }
}