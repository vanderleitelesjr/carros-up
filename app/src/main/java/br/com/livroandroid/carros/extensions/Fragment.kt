package br.com.livroandroid.carros.extensions

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

// Mostra um toast
fun Fragment.toast(message: CharSequence, length: Int = Toast.LENGTH_SHORT) =
        activity?.runOnUiThread {Toast.makeText(activity, message, length).show()}

fun Fragment.toast(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) =
        activity?.runOnUiThread { Toast.makeText(activity, message, length).show() }
