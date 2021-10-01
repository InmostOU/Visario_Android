package pro.inmost.android.visario.utils.extensions

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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

fun Fragment.snackbarWithAction(textResId: Int, actionTextResId: Int, callback: () -> Unit) {
    Snackbar.make(requireView(), requireContext().getString(textResId), Snackbar.LENGTH_SHORT)
        .setAction(actionTextResId){ callback() }
        .show()
}

fun Fragment.navigateBack() {
    findNavController().navigateUp()
}

fun Fragment.checkPermissions(vararg permissions: String, callback: (Boolean) -> Unit){
    requireContext().checkPermissions(*permissions){ callback(it) }
}

fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}
fun <T> Fragment.removeNavigationResult(key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.remove<T>(key)
}

fun Fragment.copyToClipboard(content: String, label: String = "text"){
    val clipboard: ClipboardManager =
        requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, content)
    clipboard.setPrimaryClip(clip)
}

@ColorInt
fun Fragment.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    requireContext().theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun Fragment.sendEmail(email: String){
    if (!email.isValidEmail()) throw  IllegalArgumentException("Invalid email")

    val emailIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        selector = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
        }
    }
    requireActivity().startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email)))
}

fun Fragment.openFileChooser(launcher: ActivityResultLauncher<Intent>){
    val data = Intent(Intent.ACTION_GET_CONTENT)
    data.addCategory(Intent.CATEGORY_OPENABLE)
    data.type = "application/*"
    val intent = Intent.createChooser(data, getString(R.string.choose_file))

    checkPermissions(Manifest.permission.READ_EXTERNAL_STORAGE){ granted ->
        if (granted){
            launcher.launch(intent)
        } else {
            snackbarWithAction(R.string.permissions_denied, R.string.allow){ openFileChooser(launcher) }
        }
    }
}
