package com.yuan.user.data.repository

import com.yuan.baselibrary.data.net.RetrofitFactory
import com.yuan.baselibrary.data.protocol.BaseResp
import com.yuan.user.data.api.UserApi
import com.yuan.user.data.protocol.LoginReq
import com.yuan.user.data.protocol.RegisterReq
import com.yuan.user.data.protocol.UserInfo
import rx.Observable
import javax.inject.Inject

class UserRepository @Inject constructor(){
    fun register(mobile: String, pwd: String, verifyCode: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .register(RegisterReq(mobile, pwd, verifyCode))
    }

    fun login(mobile: String, pwd: String, pushId: String): Observable<BaseResp<UserInfo>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
            .login(LoginReq(mobile, pwd, pushId))
    }
}