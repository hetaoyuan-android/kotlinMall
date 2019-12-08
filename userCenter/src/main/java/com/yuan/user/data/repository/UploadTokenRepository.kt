package com.yuan.user.data.repository

import com.yuan.baselibrary.data.net.RetrofitFactory
import com.yuan.baselibrary.data.protocol.BaseResp
import com.yuan.user.data.api.UploadApi
import com.yuan.user.data.api.UserApi
import com.yuan.user.data.protocol.*
import rx.Observable
import javax.inject.Inject

class UploadTokenRepository @Inject constructor(){
    fun getUploadToken(): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UploadApi::class.java)
            .getUploadToken()
    }
}