package com.yuan.baselibrary.injection.component

import android.content.Context
import com.yuan.baselibrary.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule:: class))
interface AppComponent {
    fun context(): Context
}