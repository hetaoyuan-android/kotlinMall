package com.yuan.user.data.api

import com.yuan.baselibrary.data.protocol.BaseResp
import com.yuan.user.data.protocol.LoginReq
import com.yuan.user.data.protocol.RegisterReq
import com.yuan.user.data.protocol.UserInfo
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UserApi {
    @POST("userCenter/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>

    @POST("userCenter/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<UserInfo>>
}