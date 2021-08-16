package pro.inmost.android.visario.ui.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import pro.inmost.android.visario.R
import kotlin.math.ceil


inline fun Fragment.navigate(direction: () -> NavDirections) {
    kotlin.runCatching {
        findNavController().navigate(direction())
    }
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(textResId: Int) {
    Toast.makeText(this, getString(textResId), Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(textResId: Int) {
    Toast.makeText(requireContext(), getString(textResId), Toast.LENGTH_SHORT).show()
}

fun Fragment.snackbar(msg: String) {
    Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.snackbar(textResId: Int) {
    Snackbar.make(requireView(), requireContext().getString(textResId), Snackbar.LENGTH_SHORT)
        .show()
}

fun Fragment.snackbarWithAction(textResId: Int, callback: () -> Unit) {
    Snackbar.make(requireView(), requireContext().getString(textResId), Snackbar.LENGTH_SHORT)
        .setAction(R.string.allow){ callback() }
        .show()
}

fun View.snackbar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}

fun View.snackbar(textResId: Int) {
    Snackbar.make(this, context.getString(textResId), Snackbar.LENGTH_SHORT).show()
}

fun Fragment.navigateBack() {
    findNavController().navigateUp()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visibility(visible: Boolean){
    visibility = if (visible) View.VISIBLE else View.GONE
}

val View.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        statusBarColor = Color.TRANSPARENT
    }
}

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}

val Context.statusBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else ceil(((if (VERSION.SDK_INT >= VERSION_CODES.M) 24 else 25) * resources.displayMetrics.density).toDouble())
            .toInt()
    }

fun SearchView.onQueryChange(query: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(q: String): Boolean {
            query(q)
            return false
        }

        override fun onQueryTextSubmit(q: String): Boolean {
            return false
        }
    })
}

fun SearchView.onQuerySubmit(query: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(q: String): Boolean {
            return false
        }

        override fun onQueryTextSubmit(q: String): Boolean {
            query(q)
            return false
        }
    })
}

fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}
fun <T> Fragment.removeNavigationResult(key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.remove<T>(key)
}

fun Context.checkPermissions(vararg permissions: String, callback: (Boolean) -> Unit){
    Dexter.withContext(this)
        .withPermissions(*permissions)
        .withListener(object : BaseMultiplePermissionsListener(){
            override fun onPermissionsChecked(p0: MultiplePermissionsReport) {
                callback(p0.areAllPermissionsGranted())
            }
        }).check()
}

fun Fragment.checkPermissions(vararg permissions: String, callback: (Boolean) -> Unit){
    Dexter.withContext(requireContext())
        .withPermissions(*permissions)
        .withListener(object : BaseMultiplePermissionsListener(){
            override fun onPermissionsChecked(p0: MultiplePermissionsReport) {
                callback(p0.areAllPermissionsGranted())
            }
        }).check()
}

inline fun CharSequence.ifNotEmpty(action: () -> Unit) {
    if (isNotBlank()) action()
}