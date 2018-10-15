package org.wit.placemark.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.helpers.readImage
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

  lateinit var presenter: PlacemarkPresenter
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  val location = Location(52.245696, -7.139102, 15f)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)

    presenter = PlacemarkPresenter(this)

    btnAdd.setOnClickListener {
      if (placemarkTitle.text.toString().isEmpty()) {
        toast(R.string.enter_placemark_title)
      } else {
        presenter.saveOrAddPlacemark(placemarkTitle.text.toString(), description.text.toString())
      }
    }

    chooseImage.setOnClickListener { showImagePicker(this, IMAGE_REQUEST) }
    placemarkLocation.setOnClickListener { startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST) }
  }

  fun showPlacemark(placemark: PlacemarkModel) {
    placemarkTitle.setText(placemark.title)
    description.setText(placemark.description)
    if (placemark.zoom != 0f) {
      location.lat = placemark.lat
      location.lng = placemark.lng
      location.zoom = placemark.zoom
    }
    placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image))
    if (placemark.image != null) {
      chooseImage.setText(R.string.change_placemark_image)
    }
    btnAdd.setText(R.string.save_placemark)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_placemark, menu)
    if (!placemarkTitle.text.isNullOrBlank()) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        presenter.deletePlacemark()
      }
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      when (requestCode) {
        IMAGE_REQUEST -> {
          presenter.imageUpdate(data.data.toString())
          placemarkImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(R.string.change_placemark_image)
        }
        LOCATION_REQUEST ->
          presenter.locationUpdate(data.extras.getParcelable<Location>("location"))
      }
    }
  }
}


