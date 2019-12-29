package com.kotlin.message.presenter.view

import com.kotlin.message.data.protocol.Message
import com.yuan.baselibrary.presenter.view.BaseView

/*
    消息列表 视图回调
 */
interface MessageView : BaseView {

    //获取消息列表回调
    fun onGetMessageResult(result:MutableList<Message>?)
}
