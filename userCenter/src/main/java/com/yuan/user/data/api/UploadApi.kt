package com.yuan.user.data.api

import com.yuan.baselibrary.data.protocol.BaseResp
import com.yuan.user.data.protocol.*
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UploadApi {
    @POST("common/getUploadToken")
    fun getUploadToken(): Observable<BaseResp<String>>

}