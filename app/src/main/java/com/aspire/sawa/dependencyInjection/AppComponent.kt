package com.aspire.sawa.dependencyInjection

import android.app.Application
import com.aspire.sawa.ui.MainActivity
import com.aspire.sawa.ui.fragments.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: HomeFragment) //TODO: take care of copy pasting the code :) , the param name should be modified to fragment

}