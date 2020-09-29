package com.example.flowerdelivery.core

import android.app.Application
import com.example.flowerdelivery.di.*
import org.koin.android.ext.android.startKoin

class OrdersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(
                connInterceptorModule,
                apiModule,
                netModule,
                repositoryModule,
                viewModelModule
            )
        )
    }

}