package pro.inmost.android.visario.utils

import android.util.Log

fun log(msg: String?){
    msg ?: return
    Log.i("VISARIO_LOG", msg)
}

fun logDebug(msg: String){
    Log.d("VISARIO_LOG", msg)
}

fun logE(msg: String){
    Log.e("VISARIO_LOG", msg)
}