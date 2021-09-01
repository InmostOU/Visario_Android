package pro.inmost.android.visario.ui.utils.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import pro.inmost.android.visario.R

inline fun Fragment.navigate(direction: () -> NavDirections) {
    kotlin.runCatching {
        findNavController().navigate(direction())
    }
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

fun Fragment.navigateBack() {
    findNavController().navigateUp()
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

fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}
fun <T> Fragment.removeNavigationResult(key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.remove<T>(key)
}
