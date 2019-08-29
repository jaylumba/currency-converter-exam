package com.jcl.exam.emapta.di

import com.exist.phr.di.BuildersModule
import com.jcl.exam.emapta.application.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by jaylumba on 05/16/2018.
 */


@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class, AppModule::class, BuildersModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: MyApplication)
}
