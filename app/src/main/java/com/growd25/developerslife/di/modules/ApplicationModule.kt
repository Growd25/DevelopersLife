package com.growd25.developerslife.di.modules

import com.growd25.developerslife.ui.fragments.DevLifeFragment
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector


@Module (includes = [AndroidInjectionModule::class])
abstract class ApplicationModule {

        @ContributesAndroidInjector
        abstract fun contributesWeatherFragment(): DevLifeFragment

}