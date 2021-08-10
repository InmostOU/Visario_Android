package pro.inmost.android.visario.data.api.utils

import android.util.Log


fun Any.logInfo(msg: String){
    Log.i(javaClass.name, msg)
}

fun Any.logError(msg: String){
    Log.e(javaClass.name, msg)
}