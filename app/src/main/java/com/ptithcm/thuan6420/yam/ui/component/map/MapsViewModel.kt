package com.ptithcm.thuan6420.yam.ui.component.map

import android.location.Geocoder
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import com.google.maps.android.PolyUtil
import com.ptithcm.thuan6420.yam.BuildConfig.MAPS_API_KEY
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.data.dto.ResponseMap
import com.ptithcm.thuan6420.yam.data.repository.MapsRepository
import com.ptithcm.thuan6420.yam.util.Constants.BASE_URL_MAP
import com.ptithcm.thuan6420.yam.util.Constants.DESTINATION
import com.ptithcm.thuan6420.yam.util.Constants.MESSAGE_DEFAULT_ERROR
import com.ptithcm.thuan6420.yam.util.Constants.MODE_DIRECTION
import com.ptithcm.thuan6420.yam.util.Constants.ORIGIN
import com.ptithcm.thuan6420.yam.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MapsViewModel @Inject constructor(private val mapsRepository: MapsRepository,
                                        private val coroutineContext: CoroutineContext): ViewModel() {

    var origin = MutableLiveData<LatLng>()

    fun getDirectionsMaps(origin: String, destination: String) = liveData(coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            val response = mapsRepository.getDirectionMap(BASE_URL_MAP, destination, origin, MODE_DIRECTION, MAPS_API_KEY)
            response.apply {
                if (isSuccessful && body()?.status == "OK") {
                    Log.e("T64", "gọi thành công")
                    Log.e("T64", Gson().toJson(body()))
                    emit(Resource.success(data = body(), message = null))
                } else if (body()?.status == "OVER_QUERY_LIMIT" ) {
                    delay(300000)
                    Log.e("T64", Gson().toJson(body()))
                } else {
                    Log.e("T64", "gọi thất bại")
                    Log.e("T64", Gson().toJson(body()))
                    emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
                }
            }
        } catch (exception: Exception) {
            Log.e("T64", "Lỗi" + exception.message.toString())
            emit(Resource.error(data = null, message = MESSAGE_DEFAULT_ERROR))
        }
    }

    fun addPointToPolylineOptions(responseMap: ResponseMap): PolylineOptions{
        val polylineOptions = PolylineOptions()
        responseMap.routes.forEach { route ->
            route.legs.forEach { leg ->
                leg.steps.forEach { step ->
                    val list = PolyUtil.decode(step.polyline.points)
                    list.forEach {
                        val position = LatLng(it.latitude, it.longitude)
                        polylineOptions.add(position)
                    }
                }
            }
        }
        return polylineOptions
    }

    internal var locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val locationList = p0.locations
            if (locationList.isEmpty()) {
                return
            }
            val location = locationList.last()
            val latLngOrigin = LatLng(location.latitude, location.longitude)
            origin.postValue(latLngOrigin)
        }
    }
}