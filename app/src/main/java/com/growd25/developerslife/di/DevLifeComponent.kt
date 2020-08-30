package com.growd25.developerslife.di

import com.growd25.developerslife.App
import com.growd25.developerslife.di.modules.ApplicationModule
import com.growd25.developerslife.di.modules.MainModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,MainModule::class])
interface DevLifeComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>

}
