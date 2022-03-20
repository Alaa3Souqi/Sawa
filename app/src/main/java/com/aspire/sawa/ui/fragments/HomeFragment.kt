package com.aspire.sawa.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat.getFont
import androidx.core.view.GravityCompat.END
import androidx.fragment.app.Fragment
import com.aspire.sawa.R
import com.aspire.sawa.adapters.CategoryAdapter
import com.aspire.sawa.adapters.PlaceAdapter
import com.aspire.sawa.databinding.FragmentHomeBinding
import com.aspire.sawa.databinding.HomeBottomSheetBinding
import com.aspire.sawa.databinding.HomeNavigationDrawerBinding
import com.aspire.sawa.ui.MainActivity
import com.aspire.sawa.unitls.Constraints.ARABIC
import com.aspire.sawa.unitls.Constraints.BLUE
import com.aspire.sawa.unitls.Constraints.ENGLISH
import com.aspire.sawa.unitls.Constraints.PINK
import com.aspire.sawa.unitls.Constraints.categoryList
import com.aspire.sawa.unitls.Constraints.placeList
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.textfield.TextInputLayout.END_ICON_CLEAR_TEXT
import com.google.android.material.textfield.TextInputLayout.END_ICON_CUSTOM
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home), RadioGroup.OnCheckedChangeListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bottomSheetBinding: HomeBottomSheetBinding
    private lateinit var drawerBinding: HomeNavigationDrawerBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        bottomSheetBinding = binding.include
        drawerBinding = binding.navDrawer
        mainActivity = activity as MainActivity

        setupBottomSheet()
        setupOnBackPress()
        setupNavigationDrawer()


        binding.run {

            drawerLayout.setScrimColor(getColor(requireContext(), R.color.transparent))

            ivMenu.setOnClickListener { drawerLayout.openDrawer(END) }

            btnCheckIn.setOnClickListener {
                //findNavController().navigate(R.id.action_homeFragment_to_QRScannerFragment)
            }

        }

        return binding.root
    }

    private fun setupNavigationDrawer() {
        drawerBinding.run {

            if (mainActivity.viewModel.getLanguage() == ARABIC) {
                rgLanguage.check(R.id.rb_arabic)
                rbArabic.typeface = getFont(requireContext(), R.font.tajawal_bold)
            } else {
                rgLanguage.check(R.id.rb_english)
                rbEnglish.typeface = getFont(requireContext(), R.font.poppins_semi_bold)
            }

            if (mainActivity.viewModel.getTheme() == PINK) {
                rgTheme.check(R.id.rb_pink)
                rbPink.typeface = getFont(requireContext(), R.font.poppins_semi_bold)

            } else {
                rgTheme.check(R.id.rb_blue)
                rbBlue.typeface = getFont(requireContext(), R.font.poppins_semi_bold)
            }

            ivBack.setOnClickListener { binding.drawerLayout.closeDrawer(END) }
            rgLanguage.setOnCheckedChangeListener(this@HomeFragment)
            rgTheme.setOnCheckedChangeListener(this@HomeFragment)

        }

    }

    private fun setupOnBackPress() {
        val behavior = from(bottomSheetBinding.root)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            when {
                binding.drawerLayout.isDrawerOpen(END) -> {
                    binding.drawerLayout.closeDrawer(END)
                }

                behavior.state == STATE_EXPANDED -> {
                    behavior.state = STATE_COLLAPSED
                }

                else -> {
                    requireActivity().finish()
                }
            }
        }
    }

    private fun setupBottomSheet() {

        bottomSheetBinding.run {

            val behavior = from(root)

            categoryRvSetup()
            placeRvSetup()

            etSearch.addTextChangedListener(textWatcher)
            etSearch.onFocusChangeListener = View.OnFocusChangeListener { _, opened ->
                if (opened)
                    behavior.state = STATE_EXPANDED
            }
            etSearch.setOnClickListener {
                behavior.state = STATE_EXPANDED
            }
        }
    }

    private fun categoryRvSetup() {
        bottomSheetBinding.rvCategory.adapter = CategoryAdapter(categoryList)
    }

    private fun placeRvSetup() {
        val placeAdapter = PlaceAdapter()

        placeAdapter.differ.submitList(placeList)
        bottomSheetBinding.rvNearbyPlaces.adapter = placeAdapter
    }

    /**
     * TextWatcher will change InputTextLayout's End Drawable to clear text when start typing.
     * If the text is empty, the search icon will appear.
     * Issue: When clicking on the clear text icon, a shadow effect on the icon will still appear even after clearing the text.
     * So, I used the delay function to solve this issue.
     */

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s.isNullOrEmpty()) {
                editTextEndDrawableSearch()
            } else {
                editTextEndDrawableClear()
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.isNullOrEmpty()) {
                editTextEndDrawableSearch()
            } else {
                editTextEndDrawableClear()
            }
        }
    }

    private fun editTextEndDrawableSearch() = MainScope().launch {
        delay(100)
        bottomSheetBinding.tilSearch.endIconMode = END_ICON_CUSTOM
        bottomSheetBinding.tilSearch.setEndIconDrawable(R.drawable.ic_search)
    }

    private fun editTextEndDrawableClear() {
        bottomSheetBinding.tilSearch.endIconMode = END_ICON_CLEAR_TEXT
        bottomSheetBinding.tilSearch.setEndIconDrawable(R.drawable.ic_clear)
    }

    override fun onCheckedChanged(radioGroup: RadioGroup, radioButton: Int) {
        drawerBinding.run {
            when (radioButton) {

                rbArabic.id -> {
                    mainActivity.viewModel.updateLanguage(ARABIC)
                }
                rbEnglish.id -> {
                    mainActivity.viewModel.updateLanguage(ENGLISH)
                }
                rbPink.id -> {
                    mainActivity.viewModel.updateTheme(PINK)
                }
                rbBlue.id -> {
                    mainActivity.viewModel.updateTheme(BLUE)
                }
            }
            mainActivity.restartActivity()
        }
    }

//    private fun recreate() {
//        mainActivity.restartActivity()
//        binding.drawerLayout.closeDrawer(END)
//        bottomSheetBinding.etSearch.clearFocus()
//    }

}
