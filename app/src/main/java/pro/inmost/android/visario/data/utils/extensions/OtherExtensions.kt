package pro.inmost.android.visario.data.utils.extensions

import com.google.gson.Gson

fun Any.toJson() = Gson().toJson(this)