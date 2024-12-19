package com.example.appmaroto

import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import com.example.appmaroto.api.DeviceService
import com.example.appmaroto.repository.DeviceRepository
import com.example.appmaroto.ui.viewmodel.CadastroViewModel
import com.example.appmaroto.ui.viewmodel.GameDetailsViewModel
import com.example.appmaroto.ui.viewmodel.GamesListViewModel
import com.example.appmaroto.ui.viewmodel.LoginViewModel
import com.example.appmaroto.ui.viewmodel.MyAccountViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.appmaroto.BuildConfig
import com.example.appmaroto.notification.CanalMaroto

val viewModelModule = module {
    viewModel { CadastroViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { GamesListViewModel() }
    viewModel { MyAccountViewModel() }
    viewModel { GameDetailsViewModel() }
}

val retrofitModule = module {
    single<Retrofit?> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<DeviceService> { get<Retrofit>().create(DeviceService::class.java) }
    single<OkHttpClient> {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}

val notificationModule = module {
    single<CanalMaroto> {CanalMaroto(get(), get())}
    single<NotificationManager> {
        get<Context>()
            .getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
}

val repositoryModule = module {
    single { DeviceRepository(get()) }
}