package com.yuan.user.data.api

import com.yuan.baselibrary.data.protocol.BaseResp
import com.yuan.user.data.protocol.RegisterReq
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UserApi {
    @POST("userCenter/register")
    fun register(@Body req: RegisterReq): Observable<BaseResp<String>>
}