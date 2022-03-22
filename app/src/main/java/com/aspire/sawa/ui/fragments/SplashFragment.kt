package com.aspire.sawa.ui.fragments

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aspire.sawa.R
import kotlinx.coroutines.*

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(750)
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }
}