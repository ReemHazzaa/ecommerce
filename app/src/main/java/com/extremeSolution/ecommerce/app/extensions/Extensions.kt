package com.extremeSolution.ecommerce.app.extensions

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.extremeSolution.ecommerce.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

const val TAG = "Extensions"

fun Activity.showAppDialog(body: String, posClicked: () -> Unit, negClicked: () -> Unit) {
    AlertDialog.Builder(this)
        .setMessage(body)
        .setPositiveButton(getString(R.string.ok)) { _, _ -> posClicked() }
        .setNegativeButton(getString(R.string.cancel)) { _, _ -> negClicked() }
        .create()
        .show()
}

fun Activity.showSnackBar(
    message: String,
    actionTxt: String = getString(R.string.ok),
    actionFn: () -> Unit = {}
) {
    try {
        Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
            .setAction(actionTxt) {
                actionFn()
            }
            .show()
    } catch (e: Exception) {
        Log.e(TAG, e.message.toString())
    }
}

fun ImageView.loadImageWithPicasso(imageUri: String) {
    Picasso.with(context).load(imageUri).fit().centerCrop()
        .placeholder(R.drawable.image_gallery)
        .error(R.drawable.error)
        .into(this);
}

fun Application.loadDrawable(@DrawableRes id: Int): Drawable {
    return AppCompatResources.getDrawable(applicationContext, id)!!
}

fun Context.loadColor(@ColorRes id: Int): Int {
    return try {
        getColor(id)
    } catch (e: Exception) {
        Log.e(TAG, e.message.toString())
        0
    }
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInVisible() {
    this.visibility = View.INVISIBLE
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit): TextWatcher {
    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            val s = editable.toString()
            afterTextChanged.invoke(s)
        }
    }

    this.addTextChangedListener(watcher)
    return watcher
}

fun Context.toast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun EditText.updateText(value: String) {
    this.setText(value)
}

fun Context.viewLink(url: String) {
    try {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    } catch (e: Exception) {
        Log.e(TAG, e.message, e)
    }
}