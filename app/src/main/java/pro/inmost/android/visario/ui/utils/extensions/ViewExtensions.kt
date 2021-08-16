package pro.inmost.android.visario.ui.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.google.android.material.snackbar.Snackbar

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