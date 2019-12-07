package com.yuan.user.injection.component

import com.yuan.baselibrary.injection.component.ActivityComponent
import com.yuan.user.injection.module.UserModule
import com.yuan.user.ui.activity.RegisterActivity
import com.yuan.baselibrary.injection.PreComponentScope
import com.yuan.user.ui.activity.LoginActivity
import dagger.Component
@PreComponentScope
@Component( dependencies = arrayOf(ActivityComponent::class) ,modules = arrayOf(UserModule::class))
interface UserComponent {
    fun inject(activity: RegisterActivity)
    fun inject(activity: LoginActivity)

}