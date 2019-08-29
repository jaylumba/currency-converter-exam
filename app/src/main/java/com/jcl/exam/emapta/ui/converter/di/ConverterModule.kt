package com.jcl.exam.emapta.ui.converter.di;

import com.jcl.exam.emapta.data.api.CurrencyService
import com.jcl.exam.emapta.data.db.MyDatabase
import com.jcl.exam.emapta.ui.converter.ConverterActivity
import com.jcl.exam.emapta.ui.converter.mvp.ConverterContract
import com.jcl.exam.emapta.ui.converter.mvp.ConverterPresenter
import com.jcl.exam.emapta.util.scheduler.AppSchedulerProvider
import dagger.Module
import dagger.Provides


@Module
class ConverterModule {

    @Provides
    internal fun provideView(activity: ConverterActivity): ConverterContract.View {
        return activity
    }

    @Provides
    internal fun providePresenter(appSchedulerProvider: AppSchedulerProvider,
                                  view: ConverterContract.View,
                                  currencyService: CurrencyService,
                                  myDatabase: MyDatabase): ConverterPresenter {
        return ConverterPresenter(appSchedulerProvider, view, currencyService, myDatabase)
    }
}