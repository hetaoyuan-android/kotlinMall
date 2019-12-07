package com.yuan.user.services.impl

import com.yuan.baselibrary.data.protocol.BaseResp
import com.yuan.baselibrary.ext.convert
import com.yuan.baselibrary.ext.convertBoolean
import com.yuan.user.data.protocol.UserInfo
import com.yuan.user.data.repository.UserRepository
import com.yuan.user.services.UserServices
import rx.Observable
import javax.inject.Inject

class UserServicesImpl @Inject constructor(): UserServices {

    @Inject
    lateinit var repository: UserRepository

    override fun register(mobile: String, verifyCode: String, pwd: String): Observable<Boolean> {
        return repository.register(mobile, pwd, verifyCode)
            .convertBoolean()
    }

    /**
     * 登录
     */
    override fun login(mobile: String, pwd: String, pushId: String): Observable<UserInfo> {
        return repository.login(mobile, pwd, pushId)
            .convert()
    }
}