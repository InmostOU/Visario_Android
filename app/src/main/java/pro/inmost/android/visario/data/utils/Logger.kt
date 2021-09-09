package pro.inmost.android.visario.data.utils

import android.util.Log


fun Any.logInfo(msg: String){
    Log.i(javaClass.name, msg)
}

fun Any.logDebug(msg: String){
    Log.d(javaClass.name, msg)
}

fun Any.logError(msg: String){
    Log.e(javaClass.name, msg)
}