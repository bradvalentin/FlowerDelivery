package com.example.flowerdelivery.di

import android.app.Application
import com.example.flowerdelivery.R
import com.example.flowerdelivery.data.network.ConnectivityInterceptor
import com.example.flowerdelivery.data.network.ConnectivityInterceptorImpl
import com.example.flowerdelivery.data.network.api.OrdersApiService
import com.example.flowerdelivery.data.repository.OrdersRepository
import com.example.flowerdelivery.data.repository.OrdersRepositoryImpl
import com.example.flowerdelivery.ui.orders.OrdersViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val apiModule = module {
    fun provideOrdersApi(retrofit: Retrofit): OrdersApiService {
        return retrofit.create(OrdersApiService::class.java)
    }

    single { provideOrdersApi(get()) }
}

val connInterceptorModule = module {

    fun provideConnectivityInterceptor(application: Application): ConnectivityInterceptor {
        return ConnectivityInterceptorImpl(application)
    }

    single { provideConnectivityInterceptor(androidApplication()) }
}

val netModule = module {

    fun provideHttpClient(connectivityInterceptor: ConnectivityInterceptor): OkHttpClient {
        val requestInterceptor = Interceptor { chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(connectivityInterceptor)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideHttpClient(get()) }
    single { provideGson() }
    single {
        val baseUrl = androidContext().getString(R.string.BASE_URL)
        provideRetrofit(get(), get(), baseUrl)
    }

}

val viewModelModule = module {

    viewModel {
        OrdersViewModel(get())
    }
}

val repositoryModule = module {
    fun provideOrdersRepository(
        apiService: OrdersApiService
    ): OrdersRepository {
        return OrdersRepositoryImpl(apiService)
    }

    single { provideOrdersRepository(get()) }
}