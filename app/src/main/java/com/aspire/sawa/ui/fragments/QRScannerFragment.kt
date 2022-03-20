package com.aspire.sawa.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aspire.sawa.R
import com.aspire.sawa.databinding.FragmentQrScannerBinding
import java.util.concurrent.ExecutorService

class QRScannerFragment : Fragment(R.layout.fragment_qr_scanner) {

    private lateinit var viewBinding: FragmentQrScannerBinding

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentQrScannerBinding.inflate(layoutInflater)

        return viewBinding.root
    }

}
