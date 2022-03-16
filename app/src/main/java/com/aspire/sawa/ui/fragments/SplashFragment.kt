package com.aspire.sawa.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aspire.sawa.R
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashFragment : Fragment(R.layout.fragment_splash), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        launch {
            delay(750)
            withContext(Dispatchers.Main){
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}