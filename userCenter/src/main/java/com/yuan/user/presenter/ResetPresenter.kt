package com.yuan.user.presenter

import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import com.yuan.user.presenter.view.ForgetView
import com.yuan.user.presenter.view.RegisterView
import com.yuan.user.presenter.view.ResetView
import com.yuan.user.services.UserServices
import javax.inject.Inject

class ResetPresenter @Inject constructor() : BasePresenter<ResetView>() {

    @Inject
    lateinit var userServices: UserServices


    /**
     * 重置密码密码
     */
    fun resetPwd(mobile: String, pwd: String) {
        /**
         * 业务逻辑
         */
//        if (!checkNetWork()) {
//            Log.e("NetWork","网络不可用")
//            return
//        }
        mView.showLoading()
        userServices.resetPwd(mobile, pwd)
            .execute(object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    if(t)
                    mView.onResetPwdResult("重置成功")
                }
            },lifecycleProvider)
    }

}