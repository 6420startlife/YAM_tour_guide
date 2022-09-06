package com.ptithcm.thuan6420.yam.ui.component.map

import com.google.android.gms.maps.model.LatLng

data class PlaceResponse(
    val name: String,
    val address: String,
    val lat: Double,
    val lng: Double
)

fun PlaceResponse.toPlace(): Place = Place(
    name = name,
    latLng = LatLng(lat, lng),
    address = address
)
