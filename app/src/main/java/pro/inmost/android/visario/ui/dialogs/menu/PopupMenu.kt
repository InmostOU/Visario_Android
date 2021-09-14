package pro.inmost.android.visario.ui.dialogs.menu

import android.content.Context
import android.view.View
import com.github.zawadz88.materialpopupmenu.popupMenu
import pro.inmost.android.visario.R
import pro.inmost.android.visario.ui.utils.extensions.getColorFromAttr

class PopupMenu(private val context: Context, private val view: View) {

    fun showMyMessageMenu(callback: (MenuItem) -> Unit) = popupMenu {
        style = R.style.Widget_MPM_Menu_Dark_CustomBackground
        section {
            item {
                label = context.getString(R.string.copy)
                labelColor = context.getColorFromAttr(android.R.attr.textColorPrimary)
                iconColor = context.getColorFromAttr(R.attr.colorOnSurface)
                icon = R.drawable.ic_baseline_content_copy_24
                this.callback = {
                    callback(MenuItem.COPY)
                }
            }
            item {
                label = context.getString(R.string.edit)
                labelColor = context.getColorFromAttr(android.R.attr.textColorPrimary)
                iconColor = context.getColorFromAttr(R.attr.colorOnSurface)
                icon = R.drawable.ic_round_edit_24
                this.callback = {
                    callback(MenuItem.EDIT)
                }
            }
        }
    }.show(context, view)

    fun showFailedMessageMenu(callback: (MenuItem) -> Unit) = popupMenu {
        style = R.style.Widget_MPM_Menu_Dark_CustomBackground
        section {
            item {
                label = context.getString(R.string.resend)
                labelColor = context.getColorFromAttr(android.R.attr.textColorPrimary)
                iconColor = context.getColorFromAttr(R.attr.colorOnSurface)
                icon = R.drawable.ic_baseline_send_24
                this.callback = {
                    callback(MenuItem.RESEND)
                }
            }
            item {
                label = context.getString(R.string.delete)
                labelColor = context.getColorFromAttr(android.R.attr.textColorPrimary)
                iconColor = context.getColorFromAttr(R.attr.colorOnSurface)
                icon = R.drawable.ic_round_delete_forever_24
                this.callback = {
                    callback(MenuItem.REMOVE)
                }
            }
        }
    }.show(context, view)

    fun showOtherMessageMenu(callback: (MenuItem) -> Unit) = popupMenu {
        style = R.style.Widget_MPM_Menu_Dark_CustomBackground
        section {
            item {
                label = context.getString(R.string.copy)
                labelColor = context.getColorFromAttr(android.R.attr.textColorPrimary)
                iconColor = context.getColorFromAttr(R.attr.colorOnSurface)
                icon = R.drawable.ic_baseline_content_copy_24
                this.callback = {
                    callback(MenuItem.COPY)
                }
            }
        }
    }.show(context, view)

    enum class MenuItem {
        EDIT,
        REMOVE,
        COPY,
        RESEND
    }
}