package com.aspire.sawa.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getColorStateList
import androidx.core.content.res.ResourcesCompat.getFont
import androidx.core.view.GravityCompat.END
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.aspire.sawa.R
import com.aspire.sawa.SawaApplication
import com.aspire.sawa.adapters.CategoryAdapter
import com.aspire.sawa.adapters.PlaceAdapter
import com.aspire.sawa.databinding.FragmentHomeBinding
import com.aspire.sawa.databinding.LayoutCheckedInBinding
import com.aspire.sawa.databinding.LayoutDialogCheckOutBinding
import com.aspire.sawa.databinding.LayoutDialogRateBinding
import com.aspire.sawa.dependencyInjection.AppComponent
import com.aspire.sawa.models.Place
import com.aspire.sawa.ui.MainActivity
import com.aspire.sawa.unitls.Constraints
import com.aspire.sawa.unitls.Constraints.ARABIC
import com.aspire.sawa.unitls.Constraints.BLUE
import com.aspire.sawa.unitls.Constraints.CHECK_IN_ID
import com.aspire.sawa.unitls.Constraints.ENGLISH
import com.aspire.sawa.unitls.Constraints.PINK
import com.aspire.sawa.unitls.Constraints.categoryList
import com.aspire.sawa.unitls.Constraints.placeList
import com.aspire.sawa.viewModels.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home), RadioGroup.OnCheckedChangeListener {

    //suggestion
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    //end of suggestion block

    private lateinit var mainActivity: MainActivity
    private lateinit var appComponent: AppComponent
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
        appComponent = (mainActivity.application as SawaApplication).appComponent
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(layoutInflater)
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBottomSheet()
        setupOnBackPress()
        setupNavigationDrawer()
        viewModel.getCheckedInPlace()

        viewModel.checkedInPlace.observe(viewLifecycleOwner) { checkInPlace ->
            val place = placeList.first { it.id == checkInPlace.id }
            setupCheckedIn(place, checkInPlace.checkInTime)

        }

        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
        savedStateHandle?.getLiveData<String>(CHECK_IN_ID)?.observe(viewLifecycleOwner) { id ->
            if (id != null) {
                viewModel.checkIn(id)
                viewModel.getCheckedInPlace()
                savedStateHandle.getLiveData<String>(CHECK_IN_ID).value = null
            }
        }

        binding.btnCheckIn.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_qrCodeFragment)
        }

        binding.drawerLayout.setScrimColor(getColor(requireContext(), R.color.transparent))

        binding.ivMenu.setOnClickListener { binding.drawerLayout.openDrawer(END) }

    }

    private fun setupCheckedIn(place: Place, checkInTime: String) {
        binding.btnGroup.isVisible = false
        binding.checkInLayout.root.isVisible = true

        binding.checkInLayout.run {

            tvCheckedInName.text = place.name
            tvBranchName.text = place.branch
            ivCheckedInLogo.setImageResource(place.image)
            tvCheckInTime.text = getString(R.string.time, checkInTime)

            fillCheckInCapacityState(place, this)

            viewModel.refreshTimer()

            var duration = ""
            viewModel.timer.observe(viewLifecycleOwner) {
                tvDuration.text = getString(R.string.duration, it)
                duration = it
            }

            cvCheckOut.setOnClickListener {
                createCheckOutDialog(place, duration)
            }
        }
    }

    private fun createCheckOutDialog(place: Place, duration: String) {

        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .create()

        val dialog = LayoutDialogCheckOutBinding.inflate(layoutInflater)

        dialog.run {
            ivPlaceLogo.setImageResource(place.image)
            tvPlaceNameDialog.text = place.name
            tvBranch.text = getString(R.string.thank_for_visiting, place.branch)
            tvSpentDuration.text = getString(R.string.spent_duration, duration)

            tvCancel.setOnClickListener {
                builder.dismiss()
            }

            clCheckOut.setOnClickListener {
                createRateDialog(place)
                viewModel.checkOut()

                binding.btnGroup.isVisible = true
                binding.checkInLayout.root.isVisible = false
                builder.dismiss()
            }

        }

        builder.run {

            setView(dialog.root)
            setCanceledOnTouchOutside(false)
            show()

        }

    }

    private fun fillCheckInCapacityState(place: Place, checkInBinding: LayoutCheckedInBinding) {
        checkInBinding.run {
            when (place.capacityState) {

                Constraints.MODERATE -> {
                    tvCapacityState.text = getString(R.string.capacity_moderator)
                    tvCapacityState.backgroundTintList =
                        getColorStateList(requireContext(), R.color.capacity_moderator)
                }

                Constraints.CLOSED -> {
                    tvCapacityState.text = getString(R.string.capacity_closed)
                    tvCapacityState.backgroundTintList =
                        getColorStateList(requireContext(), R.color.capacity_closed)
                }

                Constraints.LIGHT -> {
                    tvCapacityState.text = getString(R.string.capacity_light)
                    tvCapacityState.backgroundTintList =
                        getColorStateList(requireContext(), R.color.capacity_light)
                }

                Constraints.CROWDED -> {
                    tvCapacityState.text = getString(R.string.capacity_crowded)
                    tvCapacityState.backgroundTintList =
                        getColorStateList(requireContext(), R.color.capacity_crowded)
                }
            }
        }
    }

    private fun createRateDialog(place: Place) {

        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .create()
        val dialog = LayoutDialogRateBinding.inflate(layoutInflater)

        dialog.run {

            ivPlaceLogo.setImageResource(place.image)
            tvPlaceNameDialog.text = place.name

            tvLater.setOnClickListener {
                builder.dismiss()
            }

            rbExperience.setOnRatingBarChangeListener { _, _, _ ->
                btnSave.isEnabled = true
            }

            btnSave.setOnClickListener {
                builder.dismiss()
                Toast.makeText(
                    requireContext(),
                    "Rate : ${rbExperience.rating.toInt()}",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }

        builder.run {
            setView(dialog.root)
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    private fun setupNavigationDrawer() {
        binding.navDrawer.run {

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
        val behavior = from(binding.bottomSheet.root)

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

        binding.bottomSheet.run {

            val behavior = from(root)

            categoryRvSetup()
            placeRvSetup()

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
        binding.bottomSheet.rvCategory.adapter = CategoryAdapter(categoryList)
    }

    private fun placeRvSetup() {
        val placeAdapter = PlaceAdapter()

        placeAdapter.differ.submitList(placeList)
        binding.bottomSheet.rvNearbyPlaces.adapter = placeAdapter
    }

    override fun onCheckedChanged(radioGroup: RadioGroup, radioButton: Int) {
        binding.navDrawer.run {
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
            recreate()
        }
    }

    private fun recreate() {
        mainActivity.recreate()
        binding.drawerLayout.closeDrawer(END)
        binding.bottomSheet.etSearch.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}