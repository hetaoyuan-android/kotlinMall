package com.yuan.user.injection.component

import com.yuan.baselibrary.injection.component.ActivityComponent
import com.yuan.user.injection.module.UserModule
import com.yuan.baselibrary.injection.PreComponentScope
import com.yuan.user.injection.module.UploadModule
import com.yuan.user.ui.activity.*
import dagger.Component
@PreComponentScope
@Component( dependencies = arrayOf(ActivityComponent::class) ,modules = arrayOf(UserModule::class, UploadModule::class))
interface UserComponent {
    fun inject(activity: RegisterActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: ForgetActivity)
    fun inject(activity: ResetActivity)
    fun inject(activity: UserInfoActivity)

}