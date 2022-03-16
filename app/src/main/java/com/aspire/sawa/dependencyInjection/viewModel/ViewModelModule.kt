package com.aspire.sawa.dependencyInjection.viewModel

import androidx.lifecycle.ViewModel
import com.aspire.sawa.viewModels.SettingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SettingViewModel): ViewModel


}