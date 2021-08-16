package pro.inmost.android.visario

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pro.inmost.android.visario.di.modules.*

class VisarioApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    appModule,
                    viewModelsModule,
                    repositories,
                    dao,
                    useCases
                )
            )
        }
    }
}