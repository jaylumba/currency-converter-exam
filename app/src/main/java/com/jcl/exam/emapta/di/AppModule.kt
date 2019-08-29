package com.jcl.exam.emapta.di

import android.content.Context
import com.jcl.exam.emapta.data.api.ApiInterface
import com.jcl.exam.emapta.data.api.CurrencyService
import com.google.gson.GsonBuilder
import com.jakewharton.picasso.OkHttp3Downloader
import com.jcl.exam.emapta.BuildConfig
import com.jcl.exam.emapta.application.MyApplication
import com.jcl.exam.emapta.data.db.MyDatabase
import com.jcl.exam.emapta.data.prefs.SecuredPrefs
import com.jcl.exam.emapta.util.scheduler.AppSchedulerProvider
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by jaylumba on 05/16/2018.
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: MyApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(application: MyApplication): OkHttpClient {
        /** initialize ok http client  */
        var cacheDir = application.externalCacheDir
        if (cacheDir == null) {
            cacheDir = application.cacheDir
        }
        val cache = Cache(cacheDir!!, (10 * 1024 * 1024).toLong())

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache)
                .connectTimeout(CONNECTION_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(application: MyApplication): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl())
                .client(provideOkHttpClient(application))
                .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(application: MyApplication): ApiInterface {
        return provideRetrofit(application).create<ApiInterface>(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun providePrefs(application: MyApplication): SecuredPrefs {
        return SecuredPrefs(application)
    }

    @Singleton
    @Provides
    fun provideMyDatabase(application: MyApplication) =
            MyDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideCurrencyDao(myDatabase: MyDatabase) = myDatabase.currencyDao()

    @Singleton
    @Provides
    fun provideAccountDao(myDatabase: MyDatabase) = myDatabase.accountDao()

    @Singleton
    @Provides
    fun provideTransactionDao(myDatabase: MyDatabase) = myDatabase.transactionDao()

    @Provides
    @Singleton
    fun providePicasso(application: MyApplication): Picasso {
        return Picasso.Builder(application)
                .executor(Executors.newSingleThreadExecutor())
                .downloader(OkHttp3Downloader(provideOkHttpClient(application))).build()

    }

    @Provides
    @Singleton
    fun provideAppSchedulerProvider(): AppSchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideCurrencyService(apiInterface: ApiInterface, appSchedulerProvider: AppSchedulerProvider): CurrencyService {
        return CurrencyService(apiInterface,appSchedulerProvider)
    }

    external fun getBaseUrl(): String

    companion object {
        private val CONNECTION_TIME_OUT = 60
        private val READ_TIME_OUT = 60

        init {
            System.loadLibrary("native-lib")
        }
    }
}
