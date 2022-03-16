package com.aspire.sawa

import android.app.Application
import com.aspire.sawa.dependencyInjection.AppComponent
import com.aspire.sawa.dependencyInjection.DaggerAppComponent

class SawaApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}