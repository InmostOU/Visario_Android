package pro.inmost.android.visario.utils.extensions

import android.text.format.DateFormat

fun Long.toDateFormat() = DateFormat.format("dd-MM-yyyy", this).toString()
fun Long.toDayFormat() = DateFormat.format("EEE", this).toString()
fun Long.toTimeFormat() = DateFormat.format("hh:mm", this).toString()