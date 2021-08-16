package pro.inmost.android.visario.data.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun launchIO(action: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.IO).launch{ action() }
}

fun launchMain(action: suspend  CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.Main).launch{ action() }
}