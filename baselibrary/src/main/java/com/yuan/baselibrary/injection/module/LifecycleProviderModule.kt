package com.yuan.baselibrary.injection.module

import com.trello.rxlifecycle.LifecycleProvider
import com.yuan.baselibrary.injection.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class LifecycleProviderModule(private val lifecycleProvider: LifecycleProvider<*>){

    @ActivityScope
    @Provides
    fun providesLifecycleProvider(): LifecycleProvider<*> {
        return lifecycleProvider
    }
}