package org.wit.placemark.activities

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.placemark.main.MainApp

class PlacemarkMapsPresenter (val activity : PlacemarkMapsActivity) {

  var app: MainApp

  init {
    app = activity.application as MainApp
  }

  fun populateMap(map: GoogleMap) {
    map.uiSettings.setZoomControlsEnabled(true)
    map.setOnMarkerClickListener(activity)
    app.placemarks.findAll().forEach{
      val loc = LatLng(it.lat, it.lng)
      val options = MarkerOptions().title(it.title).position(loc)
      map.addMarker(options).tag = it.id
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
    }
  }
}