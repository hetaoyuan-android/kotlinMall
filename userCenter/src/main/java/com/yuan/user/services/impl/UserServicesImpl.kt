package com.yuan.user.services.impl

import com.yuan.baselibrary.data.protocol.BaseResp
import com.yuan.baselibrary.ext.convertBoolean
import com.yuan.baselibrary.rx.BaseExcaption
import com.yuan.baselibrary.rx.BaseFuncBoolean
import com.yuan.user.data.repository.UserRepository
import com.yuan.user.services.UserServices
import rx.Observable
import rx.functions.Func1
import javax.inject.Inject

class UserServicesImpl @Inject constructor(): UserServices {

    @Inject
    lateinit var repository: UserRepository

    override fun register(mobile: String, verifyCode: String, pwd: String): Observable<Boolean> {
        return repository.register(mobile, pwd, verifyCode)
            .convertBoolean()
    }
}