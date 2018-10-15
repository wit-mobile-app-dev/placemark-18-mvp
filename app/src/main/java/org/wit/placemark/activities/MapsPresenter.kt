package org.wit.placemark.activities

import android.app.Activity
import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.placemark.models.Location

class MapsPresenter(val activity: MapsActivity) {

  var location = Location()

  init {
    location = activity.intent.extras.getParcelable<Location>("location")
  }

  fun initMap(map: GoogleMap) {
    val loc = LatLng(location.lat, location.lng)
    val options = MarkerOptions()
        .title("Placemark")
        .snippet("GPS : " + loc.toString())
        .draggable(true)
        .position(loc)
    map.addMarker(options)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
  }

  fun updateLocation(lat: Double, lng: Double, zoom: Float) {
    location.lat = lat
    location.lng = lng
    location.zoom = zoom
  }

  fun onBackPressed() {
    val resultIntent = Intent()
    resultIntent.putExtra("location", location)
    activity.setResult(Activity.RESULT_OK, resultIntent)
    activity.finish()
  }

  fun updateMarker(marker: Marker) {
    val loc = LatLng(location.lat, location.lng)
    marker.setSnippet("GPS : " + loc.toString())
  }
}