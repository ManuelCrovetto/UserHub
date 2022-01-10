package com.example.userhub.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.userhub.R
import com.example.userhub.core.dialog.DialogLauncher
import com.example.userhub.core.ex.getCountryName
import com.example.userhub.core.ex.loadGenderImage
import com.example.userhub.core.ex.showDialogFragment
import com.example.userhub.data.constants.MapConstants.MAP_ZOOM
import com.example.userhub.data.model.Coordinates
import com.example.userhub.data.model.Name
import com.example.userhub.databinding.UserDetailFragmentBinding
import com.example.userhub.domain.model.UserInfo
import com.example.userhub.ui.adapters.map.CustomMapView
import com.example.userhub.ui.dialogs.ErrorDialog
import com.example.userhub.ui.fragments.viewmodel.UserDetailViewModel
import com.example.userhub.ui.fragments.viewstates.UserDetailViewState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailFragment @Inject constructor(
    private val dialogLauncher: DialogLauncher,
    private val glide: RequestManager
) : Fragment() {

    private var _binding: UserDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserDetailViewModel by viewModels()

    private val navArgs: UserDetailFragmentArgs? by navArgs()

    private lateinit var map: GoogleMap
    private val markerOptions = MarkerOptions()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserDetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMap()
        checkNavArgs()
    }

    private fun initMap() {
        val supportMapFragment: CustomMapView = childFragmentManager.findFragmentById(R.id.mapView) as CustomMapView
        supportMapFragment.let {
            supportMapFragment.setListener(object : CustomMapView.OnTouchListener {
                override fun onTouch() {
                    binding.nestedScrollView.requestDisallowInterceptTouchEvent(true)
                }
            })
        }
        supportMapFragment.getMapAsync { googleMap ->
            map = googleMap
            initObservers()
        }
    }

    private fun initObservers() {
        with(viewModel) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewState.collect { viewState ->
                            updateUI(viewState)
                        }
                    }
                }
            }

            result.observe(viewLifecycleOwner) { result ->
                setUpUserData(result)
            }
        }
    }

    private fun setUpUserData(result: UserInfo) {
        with(binding) {
            glide.load(result.response.picture.large).into(binding.shapeableImageView)
            tvName.text = getString(
                R.string.user_name_placeholder,
                result.response.name.first,
                result.response.name.last
            )
            tvAge.text = result.response.dob.age.toString()
            tvNationality.text = result.response.nat.getCountryName()
            tvEmail.text = result.response.email
            binding.ivGender.loadGenderImage(result.response.gender)
            setUpLocationInMap(
                result.response.location.coordinates,
                result.response.name,
                result.response.email
            )
        }
    }

    private fun setUpLocationInMap(coordinates: Coordinates, name: Name, email: String) {
        if (::map.isInitialized && coordinates.latitude.isNotEmpty() && coordinates.longitude.isNotEmpty()){
            val builder = LatLngBounds.builder()
            val location = LatLng(coordinates.latitude.toDouble(), coordinates.longitude.toDouble())
            with(markerOptions) {
                position(location)
                title(getString(R.string.user_name_placeholder, name.first, name.last))
                snippet(email)
            }
            builder.include(location)
            map.addMarker(markerOptions)

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, MAP_ZOOM))
        }
    }

    private fun updateUI(viewState: UserDetailViewState) {
        if (viewState.error) showErrorDialog()
    }

    private fun showErrorDialog() {
        ErrorDialog.create(
            action = ErrorDialog.Action(getString(R.string.ok_placeholder)) {
                it.dismiss()
            }
        ).showDialogFragment(dialogLauncher, requireActivity())
    }

    private fun checkNavArgs() {
        viewModel.checkArgs(navArgs)
    }
}