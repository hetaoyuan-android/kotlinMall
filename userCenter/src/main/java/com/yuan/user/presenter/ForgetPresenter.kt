package com.yuan.user.presenter

import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import com.yuan.user.presenter.view.ForgetView
import com.yuan.user.presenter.view.RegisterView
import com.yuan.user.services.UserServices
import javax.inject.Inject

class ForgetPresenter @Inject constructor() : BasePresenter<ForgetView>() {

    @Inject
    lateinit var userServices: UserServices


    /**
     * 忘记密码
     */
    fun forgetPwd(mobile: String, verifyCode: String) {
        /**
         * 业务逻辑
         */
//        if (!checkNetWork()) {
//            Log.e("NetWork","网络不可用")
//            return
//        }
        mView.showLoading()
        userServices.forgetPwd(mobile, verifyCode)
            .execute(object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    if(t)
                    mView.onForgetPwdResult("验证成功")
                }
            },lifecycleProvider)
    }

    /**
     * 登录
     */
}