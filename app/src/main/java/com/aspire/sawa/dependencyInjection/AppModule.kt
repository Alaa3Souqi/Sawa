package com.aspire.sawa.dependencyInjection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.aspire.sawa.unitls.Constraints
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(Constraints.MY_PREFS_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Singleton
    @Provides
    @Named("language")
    fun provideLocaleLanguage(): String =
        Locale.getDefault().language

}