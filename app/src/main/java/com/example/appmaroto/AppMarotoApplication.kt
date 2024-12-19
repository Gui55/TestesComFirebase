package com.example.appmaroto

import android.app.Application
import com.example.appmaroto.notification.CanalMaroto
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AppMarotoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppMarotoApplication)
            modules(
                viewModelModule,
                retrofitModule,
                repositoryModule,
                notificationModule
            )
        }
        val canalMaroto: CanalMaroto by inject()
        canalMaroto.createCanalMaroto()
    }
}