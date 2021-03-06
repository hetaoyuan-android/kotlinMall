package com.yuan.user.injection.module

import com.yuan.user.services.UserServices
import com.yuan.user.services.impl.UserServicesImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UserModule {

    @Provides
    fun providesUserServices(services: UserServicesImpl): UserServices {
        return services
    }
}