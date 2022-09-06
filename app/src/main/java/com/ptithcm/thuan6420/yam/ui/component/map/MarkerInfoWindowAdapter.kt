package com.ptithcm.thuan6420.yam.ui.component.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.ptithcm.thuan6420.yam.R
import com.ptithcm.thuan6420.yam.databinding.MarkerInfoContentsBinding

class MarkerInfoWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {
    private lateinit var viewBinding: MarkerInfoContentsBinding
    override fun getInfoContents(marker: Marker): View? {
        val place = marker.tag as? Place ?: return null

        viewBinding = MarkerInfoContentsBinding.inflate(LayoutInflater.from(context))
        viewBinding.textViewTitle.text = place.name
        viewBinding.textViewAddress.text = place.address

        return viewBinding.root
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}
