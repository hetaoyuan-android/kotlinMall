package com.yuan.user.services

import rx.Observable

interface UploadServices {
    fun getUploadToken(): Observable<String>

}