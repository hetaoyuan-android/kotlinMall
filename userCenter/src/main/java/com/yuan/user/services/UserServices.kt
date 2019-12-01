package com.yuan.user.services

import rx.Observable

interface UserServices {
    fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean>
}