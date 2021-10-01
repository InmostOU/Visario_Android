package pro.inmost.android.visario

import android.app.Application
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.ios.IosEmojiProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import pro.inmost.android.visario.di.modules.*
import pro.inmost.android.visario.utils.di.appModule


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
        EmojiManager.install(IosEmojiProvider())
    }


    /**
     * Reload Koin modules (use after user logout)
     *
     */
    fun reloadModules() {
        koin?.unloadModules(modules)
        koin?.modules(modules)
    }

    companion object {
        var instance: VisarioApp? = null
    }
}