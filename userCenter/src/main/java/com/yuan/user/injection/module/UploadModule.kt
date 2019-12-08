package com.yuan.user.injection.module

import com.yuan.user.services.UploadServices
import com.yuan.user.services.UserServices
import com.yuan.user.services.impl.UploadServicesImpl
import com.yuan.user.services.impl.UserServicesImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UploadModule {

    @Provides
    fun providesUploadServices(services: UploadServicesImpl): UploadServices {
        return services
    }
}