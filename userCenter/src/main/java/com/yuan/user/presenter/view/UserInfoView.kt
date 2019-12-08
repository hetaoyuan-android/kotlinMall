package com.yuan.user.presenter.view

import com.yuan.baselibrary.presenter.view.BaseView
import com.yuan.user.data.protocol.UserInfo

interface UserInfoView: BaseView {

    fun onGetUploadGetTokenResult(result: String)
    fun onEditUserResult(result:UserInfo)
}