package org.wit.placemark.activities

import com.google.android.gms.maps.GoogleMap
import org.wit.placemark.main.MainApp

class PlacemarkListPresenter (val activity : PlacemarkListActivity) {

  var app: MainApp

  init {
    app = activity.application as MainApp
  }

  fun getPlacemarks() = app.placemarks.findAll()
}