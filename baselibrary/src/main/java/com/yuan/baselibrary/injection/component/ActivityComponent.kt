package com.yuan.baselibrary.injection.component

import android.app.Activity
import android.content.Context
import com.yuan.baselibrary.injection.ActivityScope
import com.yuan.baselibrary.injection.module.ActivityModule
import com.yuan.baselibrary.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@ActivityScope
@Component( dependencies = arrayOf(AppComponent::class),modules = arrayOf(ActivityModule:: class))
interface ActivityComponent {
    fun activity(): Activity
}