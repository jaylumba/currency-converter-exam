package com.exist.phr.di

import com.jcl.exam.emapta.ui.converter.ConverterActivity
import com.jcl.exam.emapta.ui.converter.di.ConverterModule
import com.jcl.exam.emapta.ui.main.MainActivity
import com.jcl.exam.emapta.ui.main.di.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jaylumba on 11/18/2017.
 */

@Module
abstract class BuildersModule{

    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ConverterModule::class])
    internal abstract fun bindConverterActivity(): ConverterActivity

}
