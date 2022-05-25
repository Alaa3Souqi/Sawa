package com.aspire.sawa.dependencyInjection.viewModel

import androidx.lifecycle.ViewModel
import com.aspire.sawa.viewModels.HomeViewModel
import com.aspire.sawa.viewModels.SettingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun settingViewModel(viewModel: SettingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

}