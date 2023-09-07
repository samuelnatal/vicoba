package com.example.vicoba.data.di

import com.example.vicoba.data.network.VicobaApiService
import com.example.vicoba.data.repositories.AddressRepository
import com.example.vicoba.data.repositories.DebtRepository
import com.example.vicoba.data.repositories.DefaultAddressRepository
import com.example.vicoba.data.repositories.DefaultDebtRepository
import com.example.vicoba.data.repositories.DefaultKikobaMemberRepository
import com.example.vicoba.data.repositories.DefaultKikobaRepository
import com.example.vicoba.data.repositories.DefaultLoanRepository
import com.example.vicoba.data.repositories.DefaultNotificationRepository
import com.example.vicoba.data.repositories.DefaultOccupationRepository
import com.example.vicoba.data.repositories.DefaultSearchRepository
import com.example.vicoba.data.repositories.DefaultShareRepository
import com.example.vicoba.data.repositories.DefaultUserRepository
import com.example.vicoba.data.repositories.KikobaMemberRepository
import com.example.vicoba.data.repositories.KikobaRepository
import com.example.vicoba.data.repositories.LoanRepository
import com.example.vicoba.data.repositories.NotificationRepository
import com.example.vicoba.data.repositories.OccupationRepository
import com.example.vicoba.data.repositories.SearchRepository
import com.example.vicoba.data.repositories.ShareRepository
import com.example.vicoba.data.repositories.UserRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * App container is contains the dependencies that the app requires.
 * These dependencies are used across the whole application,
 * so they need to be in a common place that all activities can use.
 */
interface AppContainer {
    val userRepository: UserRepository
    val occupationRepository: OccupationRepository
    val addressRepository: AddressRepository
    val kikobaRepository: KikobaRepository
    val kikobaMemberRepository: KikobaMemberRepository
    val notificationRepository: NotificationRepository
    val searchRepository: SearchRepository
    val loanRepository: LoanRepository
    val shareRepository: ShareRepository
    val debtRepository: DebtRepository
}

class DefaultAppContainer : AppContainer {
    /**
     * Below is an interceptor function that keeps logs
     * of the http requests made by the retrofit library to the internet*/
    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private val loggingInterceptor = provideLoggingInterceptor()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    /**BASE_URL represent the host on which Vicoba database server is hosted*/
    private val baseURL =
        "http://10.0.2.2:3000"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseURL)
        .build()

    /**
     * A retrofitService hold an instance of the retrofit that utilizes VicobaApiService
     * with a list of REST HTTP Operations and end points required by the app
     * for data source
     */
    private val retrofitService: VicobaApiService by lazy {
        retrofit.create(VicobaApiService::class.java)
    }

    override val userRepository: UserRepository by lazy {
        DefaultUserRepository(retrofitService)
    }

    override val occupationRepository: OccupationRepository by lazy {
        DefaultOccupationRepository(retrofitService)
    }

    override val addressRepository: AddressRepository by lazy {
        DefaultAddressRepository(retrofitService)
    }

    override val kikobaRepository: KikobaRepository by lazy {
        DefaultKikobaRepository(retrofitService)
    }

    override val kikobaMemberRepository: KikobaMemberRepository by lazy {
        DefaultKikobaMemberRepository(retrofitService)
    }

    override val notificationRepository: NotificationRepository by lazy {
        DefaultNotificationRepository(retrofitService)
    }

    override val searchRepository: SearchRepository by lazy {
        DefaultSearchRepository(retrofitService)
    }
    override val loanRepository: LoanRepository by lazy {
        DefaultLoanRepository(retrofitService)
    }

    override val shareRepository: ShareRepository by lazy {
        DefaultShareRepository(retrofitService)
    }

    override val debtRepository: DebtRepository by lazy {
        DefaultDebtRepository(retrofitService)
    }

}