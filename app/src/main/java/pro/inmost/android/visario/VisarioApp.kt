package pro.inmost.android.visario

import android.app.Application
import android.content.res.Resources
import org.koin.core.context.startKoin
import pro.inmost.android.visario.di.appModule
import pro.inmost.android.visario.di.viewModelsModule

class VisarioApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Companion.resources = applicationContext.resources
        startKoin {
            modules(listOf(appModule, viewModelsModule))
        }
    }

    companion object{
        lateinit var resources: Resources
    }
}