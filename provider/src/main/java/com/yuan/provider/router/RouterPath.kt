package com.yuan.provider.router

object RouterPath {
    //用户模块
    class UserCenter {
        companion object {
            const val PATH_LOGIN = "/userCenter/login"
        }
    }

    //订单模块
    class OrderLibrary{

        companion object {
            const val PATH_ORDER_CONFIRM = "/orderlibrary/confirm"
        }
    }

    //支付模块
    class PaySDK{
        companion object {
            const val PATH_PAY = "/paySDK/pay"
        }
    }

    //消息模块
    class MessageCenter{
        companion object {
            const val PATH_MESSAGE_PUSH = "/messageCenter/push"
            const val PATH_MESSAGE_ORDER = "/messageCenter/order"
        }
    }
}