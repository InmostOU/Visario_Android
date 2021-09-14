package pro.inmost.android.visario.ui.utils.extensions

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import com.google.android.material.snackbar.Snackbar
import pro.inmost.android.visario.R
import pro.inmost.android.visario.ui.utils.showKeyboard


fun View.snackbar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}

fun View.snackbar(textResId: Int) {
    Snackbar.make(this, context.getString(textResId), Snackbar.LENGTH_SHORT).show()
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

fun View.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun View.toast(textResId: Int) {
    Toast.makeText(context, context.getString(textResId), Toast.LENGTH_SHORT).show()
}

val View.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)


fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
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

val View.isPortraitOrientation: Boolean get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
val View.isLandscapeOrientation: Boolean get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun EditText.showKeyboardAndMoveCursorToEnd() {
    showKeyboard()
    requestFocus()
    post { setSelection(text.length) }
}

/**
 * Moves icons from the PopupMenu's MenuItems' icon fields into the menu title as a Spannable with the icon and title text.
 */
fun PopupMenu.insertMenuItemIcons(context: Context) {
    if (hasIcon(menu)) {
        for (i in 0 until menu.size()) {
            insertMenuItemIcon(context, menu.getItem(i))
        }
    }
}

/**
 * @return true if the menu has at least one MenuItem with an icon.
 */
private fun hasIcon(menu: Menu): Boolean {
    for (i in 0 until menu.size()) {
        if (menu.getItem(i).icon != null) return true
    }
    return false
}

/**
 * Converts the given MenuItem's title into a Spannable containing both its icon and title.
 */
private fun insertMenuItemIcon(context: Context, menuItem: MenuItem) {
    var icon: Drawable? = menuItem.icon

    // If there's no icon, we insert a transparent one to keep the title aligned with the items
    // which do have icons.
    if (icon == null) icon = ColorDrawable(Color.TRANSPARENT)
    val iconSize: Int = context.resources.getDimensionPixelSize(R.dimen.menu_item_icon_size)
    icon.setBounds(0, 0, iconSize, iconSize)
    val imageSpan = ImageSpan(icon)

    // Add a space placeholder for the icon, before the title.
    val ssb = SpannableStringBuilder("       " + menuItem.title)

    // Replace the space placeholder with the icon.
    ssb.setSpan(imageSpan, 1, 2, 0)
    menuItem.title = ssb
    // Set the icon to null just in case, on some weird devices, they've customized Android to display
    // the icon in the menu... we don't want two icons to appear.
    menuItem.icon = null
}
