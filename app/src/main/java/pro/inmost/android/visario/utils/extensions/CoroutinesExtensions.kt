package pro.inmost.android.visario.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * Global function to simplify launching new coroutine in IO thread
 *
 * @param action
 * @return new Job of this coroutine
 */
fun launchIO(action: suspend CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.IO).launch{ action() }
}

/**
 * Global function to simplify launching new coroutine in Main thread
 *
 * @param action
 * @return new Job of this coroutine
 */
fun launchMain(action: suspend  CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.Main).launch{ action() }
}