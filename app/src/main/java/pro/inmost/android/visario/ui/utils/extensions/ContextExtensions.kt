package pro.inmost.android.visario.ui.utils.extensions

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import kotlin.math.ceil

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(textResId: Int) {
    Toast.makeText(this, getString(textResId), Toast.LENGTH_SHORT).show()
}

val Context.statusBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else ceil(((if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) 24 else 25) * resources.displayMetrics.density).toDouble())
            .toInt()
    }

val Context.isPortraitOrientation: Boolean get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
val Context.isLandscapeOrientation: Boolean get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


fun Context.checkPermissions(vararg permissions: String, callback: (Boolean) -> Unit){
    Dexter.withContext(this)
        .withPermissions(*permissions)
        .withListener(object : BaseMultiplePermissionsListener(){
            override fun onPermissionsChecked(p0: MultiplePermissionsReport) {
                callback(p0.areAllPermissionsGranted())
            }
        }).check()
}