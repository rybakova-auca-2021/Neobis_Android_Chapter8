package com.example.neobis_android_chapter8.utils

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

object ImageHelper {
    const val PICK_IMAGE_REQUEST = 1

    fun openGallery(fragment: Fragment) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        fragment.startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    fun handleImageSelection(
        fragment: Fragment,
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        addButton: ImageView,
        imageContainer: ViewGroup
    ) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val imageView = ImageView(fragment.requireContext())
            val layoutParams = LinearLayout.LayoutParams(
                addButton.width,
                addButton.height
            )
            layoutParams.setMargins(6, 0, 6, 0) // Set margin of 6dp
            imageView.layoutParams = layoutParams
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageURI(imageUri)
            imageView.clipToOutline = true
            imageContainer.addView(imageView)

            imageUri?.let {
                Utils.addImageUri(it)
            }
        }
    }
}