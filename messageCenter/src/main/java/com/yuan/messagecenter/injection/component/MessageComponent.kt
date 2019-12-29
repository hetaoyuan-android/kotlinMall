package com.kotlin.message.injection.component


import com.kotlin.message.injection.module.MessageModule
import com.kotlin.message.ui.fragment.MessageFragment
import com.yuan.baselibrary.injection.PreComponentScope
import com.yuan.baselibrary.injection.component.ActivityComponent
import dagger.Component

/*
    消息模块注入组件
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(MessageModule::class))
interface MessageComponent{
    fun inject(fragment:MessageFragment)
}
