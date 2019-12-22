package com.yuan.provider.router

object RouterPath {
    //用户模块
    class UserCenter {
        companion object {
            const val PATH_LOGIN = "/userCenter/login"
        }
    }

    //订单模块
    class OrderCenter{
        companion object {
            const val PATH_ORDER_CONFIRM = "/orderCenter/confirm"
        }
    }
}