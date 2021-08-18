package pro.inmost.android.visario

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import pro.inmost.android.visario.di.modules.*

class VisarioApp : Application() {
    private var koin: KoinApplication? = null
    private val modules = listOf(
        appModule,
        viewModelsModule,
        repositories,
        dao,
        useCases
    )

    override fun onCreate() {
        super.onCreate()
        instance = this
        koin = startKoin {
            androidContext(applicationContext)
            modules(modules)
        }
    }

    fun reloadModules() {
        koin?.unloadModules(modules)
        koin?.modules(modules)
    }

    companion object {
        var instance: VisarioApp? = null
    }
}