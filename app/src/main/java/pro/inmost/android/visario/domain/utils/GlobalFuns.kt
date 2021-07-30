package pro.inmost.android.visario.domain.utils

import android.util.Log

fun log(msg: String?){
    msg ?: return
    Log.i("VISARIO_LOG", msg)
}