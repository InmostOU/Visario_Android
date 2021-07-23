package pro.inmost.android.visario

import android.app.Application
import org.koin.core.context.startKoin
import pro.inmost.android.visario.di.appModule
import pro.inmost.android.visario.di.viewModelsModule

class VisarioApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(appModule, viewModelsModule))
        }
    }
}