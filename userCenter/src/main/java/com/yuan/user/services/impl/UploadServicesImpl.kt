package com.yuan.user.services.impl

import com.yuan.baselibrary.ext.convert
import com.yuan.user.data.repository.UploadTokenRepository
import com.yuan.user.services.UploadServices
import rx.Observable
import javax.inject.Inject

class UploadServicesImpl @Inject constructor(): UploadServices {

    @Inject
    lateinit var repository: UploadTokenRepository


    override fun getUploadToken(): Observable<String> {
        return repository.getUploadToken().convert()
    }


}