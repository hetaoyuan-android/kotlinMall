package com.yuan.baselibrary.injection.module

import android.content.Context
import com.yuan.baselibrary.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: BaseApplication){

    @Singleton
    @Provides
    fun providesContext(): Context {
        return context
    }
}