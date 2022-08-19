package com.example.socialmedia.core.extentions

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.google.android.material.snackbar.Snackbar

fun NavController.deleteCurrentFragmentAfterNavigate() = NavOptions.Builder()
    .setPopUpTo(this.currentDestination!!.id, true)
    .build()

fun NavController.navigateSafely(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null,
) {
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(resId, args, navOptions, navExtras)
    }
}



fun Fragment.snackbar(message: String) {
    requireView().hideKeyboard()
    Snackbar.make(
        requireView(),
        message,
        Snackbar.LENGTH_LONG
    ).show()

}

infix fun View.snackbar(message: String) {
    hideKeyboard()
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).show()

}

fun View.hideKeyboard() {
    val inputManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}
