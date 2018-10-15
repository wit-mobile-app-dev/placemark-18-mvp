package org.wit.placemark.activities

import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel

class PlacemarkPresenter(val activity: PlacemarkActivity) {

  var placemark = PlacemarkModel()
  var app: MainApp
  var edit = false;

  init {
    app = activity.application as MainApp
    if (activity.intent.hasExtra("placemark_edit")) {
      edit = true
      placemark = activity.intent.extras.getParcelable<PlacemarkModel>("placemark_edit")
      activity.showPlacemark(placemark)
    }
  }

  fun saveOrAddPlacemark(title: String, description: String) {
    placemark.title = title;
    placemark.description = description;
    if (edit) {
      app.placemarks.update(placemark)
    } else {
      app.placemarks.create(placemark)
    }
    activity.finish()
  }

  fun deletePlacemark() {
    app.placemarks.delete(placemark)
    activity.finish()
  }

  fun imageUpdate(image: String) {
    placemark.image = image
  }

  fun locationUpdate(location: Location) {
    placemark.lat = location.lat
    placemark.lng = location.lng
    placemark.zoom = location.zoom
  }
}