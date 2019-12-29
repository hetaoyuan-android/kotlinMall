package com.yuan.messagecenter.receiver

import android.content.Context
import cn.jpush.android.service.JPushMessageReceiver
import cn.jpush.android.api.JPushMessage



class MyJPushMessageReceiver : JPushMessageReceiver() {
    override fun onTagOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onTagOperatorResult(context, jPushMessage)
    }

    override fun onCheckTagOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage)
    }

    override fun onAliasOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage)
    }

    override fun onMobileNumberOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onMobileNumberOperatorResult(context, jPushMessage)
    }
}