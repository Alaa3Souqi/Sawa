package com.aspire.sawa.ui

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aspire.sawa.R
import com.aspire.sawa.SawaApplication
import com.aspire.sawa.dependencyInjection.AppComponent
import com.aspire.sawa.unitls.Constraints.BLUE
import com.aspire.sawa.viewModels.SettingViewModel
import java.util.*
import javax.inject.Inject

@Suppress("DEPRECATION")
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
        setLocale(viewModel.getLanguage())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode,"TN")
        Locale.setDefault(locale)
        val resources: Resources = this.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}