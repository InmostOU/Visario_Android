package pro.inmost.android.visario.ui.utils

import android.util.Log

fun log(msg: String?){
    msg ?: return
    Log.i("VISARIO_LOG", msg)
}