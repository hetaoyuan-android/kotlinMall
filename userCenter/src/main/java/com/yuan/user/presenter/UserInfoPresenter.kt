package com.yuan.user.presenter

import com.yuan.baselibrary.ext.execute
import com.yuan.baselibrary.presenter.BasePresenter
import com.yuan.baselibrary.rx.BaseSubscriber
import com.yuan.user.data.protocol.UserInfo
import com.yuan.user.presenter.view.UserInfoView
import com.yuan.user.services.UploadServices
import com.yuan.user.services.UserServices
import javax.inject.Inject

class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>() {

    @Inject
    lateinit var userServices: UserServices

    @Inject
    lateinit var uploadServices: UploadServices

    fun getUploadToken() {
        mView.showLoading()
        uploadServices.getUploadToken().execute(object : BaseSubscriber<String>(mView){
            override fun onNext(t: String) {
               mView.onGetUploadGetTokenResult(t)
            }
        },lifecycleProvider)
    }

    fun editUser(userIcon: String, userName: String, userGender: String, userSign: String) {
        mView.showLoading()
        userServices.editUser(userIcon, userName, userGender, userSign).execute(object : BaseSubscriber<UserInfo>(mView){
            override fun onNext(t: UserInfo) {
                mView.onEditUserResult(t)
            }
        },lifecycleProvider)
    }

}