package com.aspire.sawa.ui

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aspire.sawa.R
import com.aspire.sawa.SawaApplication
import com.aspire.sawa.dependencyInjection.AppComponent
import com.aspire.sawa.unitls.Constraints.BLUE
import com.aspire.sawa.viewModels.SettingViewModel
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appComponent: AppComponent

    @Inject
    lateinit var viewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        appComponent = (application as SawaApplication).appComponent
        appComponent.inject(this)

        if (viewModel.getTheme() == BLUE) {
            setTheme(R.style.Theme_Sawa_Blue)
        } else {
            setTheme(R.style.Theme_Sawa_Pink)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setLocale(viewModel.getLanguage())

    }

    fun restartActivity() {
        val intent = intent
        finish()
        startActivity(intent)
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = this.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}