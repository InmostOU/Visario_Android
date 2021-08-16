package pro.inmost.android.visario.di.modules

import org.koin.dsl.module
import pro.inmost.android.visario.data.repositories.*

val repositories = module {
    factory { AccountRepositoryImpl(get()) }
    factory { ChannelsRepositoryImpl(get(), get(), get<MessagesRepositoryImpl>()) }
    factory { MessagesRepositoryImpl(get(), get()) }
    factory { ContactsRepositoryImpl(get(), get()) }
    factory { ProfileRepositoryImpl(get(), get()) }
}