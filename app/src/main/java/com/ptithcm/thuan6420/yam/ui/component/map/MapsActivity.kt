package com.ptithcm.thuan6420.yam.ui.component.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.data.dto.ResponseMap
import com.ptithcm.thuan6420.yam.databinding.ActivityMapsBinding
import com.ptithcm.thuan6420.yam.ui.base.BaseActivity
import com.ptithcm.thuan6420.yam.util.Constants.DESTINATION
import com.ptithcm.thuan6420.yam.util.Constants.REQUEST_LOCATION_PERMISSION
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapsBinding
    private val viewModel: MapsViewModel by viewModels()

    @Inject lateinit var fusedLocationClient: FusedLocationProviderClient
    @Inject lateinit var locationRequest: LocationRequest
    private lateinit var mGoogleMap: GoogleMap

    override fun setEvent() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun initViewBinding() {
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun observerViewModel() {
        val bundle = intent.extras ?: return
        val destination = bundle.getString(DESTINATION) ?: return
        viewModel.origin.observe(this) {
            val origin = "${it.latitude}, ${it.longitude}"
            mGoogleMap.addMarker(MarkerOptions().apply {
                position(it)
                title("Your location")
                icon(locationIcon)
            })
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
            geocoding(origin, destination)
        }
    }

    private fun geocoding(origin: String,destinationAddress: String) {
        val geoCoder = Geocoder(this)
        val toLocation = geoCoder.getFromLocationName(
            destinationAddress, 1
        )
        val destination = "${toLocation.first().latitude}, ${toLocation.first().longitude}"
        mGoogleMap.addMarker(MarkerOptions().apply {
            position(LatLng(toLocation.first().latitude, toLocation.first().longitude))
            title(toLocation.first().getAddressLine(0))
            icon(locationIcon)
        })
        direction(origin, destination)
    }

    private val locationIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(this, R.color.red)
        BitmapHelper.vectorToBitmap(this, R.drawable.ic_baseline_location_on_24, color)
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_LOCATION_PERMISSION)
            false
        } else {
            true
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (isLocationPermissionGranted()) {
            fusedLocationClient.requestLocationUpdates(locationRequest,
                viewModel.locationCallback, Looper.myLooper())
            mGoogleMap.isMyLocationEnabled = true
        } else {
            Log.e("Maps", "Chưa cấp permission")
        }
    }

    private fun direction(origin: String, destination: String) {
        viewModel.getDirectionsMaps(origin, destination).observe(this) {
            submit(1, it.status, it.message, it.data)
        }
    }

    override fun onError(tasks: Int, message: String?) {
    }

    override fun onLoading(tasks: Int) {
    }

    override fun onSuccess(tasks: Int, message: String?, data: Any?) {
        val result = data as ResponseMap
        val polylineOptions = viewModel.addPointToPolylineOptions(result)
        polylineOptions.width(10F)
        polylineOptions.color(ContextCompat.getColor(this, R.color.red))
        polylineOptions.geodesic(true)
        mGoogleMap.addPolyline(polylineOptions)
    }


    override fun onMapReady(p0: GoogleMap) {
        mGoogleMap = p0
        getCurrentLocation()
    }
}