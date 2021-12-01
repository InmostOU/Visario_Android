package pro.inmost.android.visario

import android.app.Application
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.ios.IosEmojiProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import pro.inmost.android.visario.di.modules.useCases
import pro.inmost.android.visario.utils.di.appModule
import pro.inmost.android.visario.utils.di.dao
import pro.inmost.android.visario.utils.di.repositories
import pro.inmost.android.visario.utils.di.viewModelsModule


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