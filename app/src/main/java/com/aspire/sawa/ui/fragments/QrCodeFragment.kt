package com.aspire.sawa.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.aspire.sawa.R
import com.aspire.sawa.databinding.FragmentQrCodeBinding
import com.aspire.sawa.unitls.Constraints.CHECK_IN_ID
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback

class QrCodeFragment : Fragment(R.layout.fragment_qr_code) {

    private var _binding: FragmentQrCodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var codeScanner: CodeScanner

    private val requestCameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        if (isGranted) {
            codeScanner.startPreview()
        } else {
            Toast.makeText(context, getString(R.string.access_camera), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrCodeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestCameraPermission.launch(Manifest.permission.CAMERA)

        codeScanner = CodeScanner(requireActivity(), binding.scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                val navController = findNavController()
                setFragmentResult(CHECK_IN_ID, bundleOf(CHECK_IN_ID to it.text))
//              navController.previousBackStackEntry?.savedStateHandle?.set(CHECK_IN_ID, it.text)
                navController.popBackStack()
            }
        }

        binding.ivBackQr.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}