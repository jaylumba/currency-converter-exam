package com.jcl.exam.emapta.ui.main.di;

import com.jcl.exam.emapta.data.db.dao.AccountDao
import com.jcl.exam.emapta.ui.main.MainActivity
import com.jcl.exam.emapta.ui.main.mvp.MainContract
import com.jcl.exam.emapta.ui.main.mvp.MainPresenter
import com.jcl.exam.emapta.util.scheduler.AppSchedulerProvider
import dagger.Module
import dagger.Provides


@Module
class MainModule {

    @Provides
    internal fun provideView(activity: MainActivity): MainContract.View {
        return activity
    }

    @Provides
    internal fun providePresenter(appSchedulerProvider: AppSchedulerProvider, view: MainContract.View, accountDao: AccountDao): MainPresenter {
        return MainPresenter(appSchedulerProvider, view, accountDao)
    }
}