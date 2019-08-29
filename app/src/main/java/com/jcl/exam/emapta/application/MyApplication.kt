package com.jcl.exam.emapta.application

import android.app.Activity
import android.app.Service
import androidx.multidex.MultiDexApplication
import com.jcl.exam.emapta.BuildConfig
import com.jcl.exam.emapta.R
import com.jcl.exam.emapta.di.DaggerAppComponent
import com.jcl.exam.emapta.encryption.AesCbcWithIntegrity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.security.GeneralSecurityException
import javax.inject.Inject

/**
 * Created by jaylumba on 05/16/2018.
 */
class MyApplication : MultiDexApplication(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()

        /** initialize timber  */
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        /** initialize calligraphy  */
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/helvetica-normal.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())


        /** initialize dagger */
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        /** initialize encryption key */
        try {
            keys = AesCbcWithIntegrity.generateKeyFromPassword(getSecretKey(), getSecretKey())
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return dispatchingServiceInjector
    }

    external fun getSecretKey(): String

    companion object {

        var keys: AesCbcWithIntegrity.SecretKeys? = null
            private set

        init {
            System.loadLibrary("native-lib")
        }
    }
}