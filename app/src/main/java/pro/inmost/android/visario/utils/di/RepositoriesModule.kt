package pro.inmost.android.visario.utils.di

import org.koin.dsl.module
import pro.inmost.android.visario.data.repositories.*

/**
 * Repository module for Koin dependency framework
 */
val repositories = module {
    factory { AccountRepositoryImpl(get()) }
    factory { ChannelsRepositoryImpl(get(), get(),get(), get<MessagesRepositoryImpl>()) }
    factory { MessagesRepositoryImpl(get(), get(), get()) }
    factory { ContactsRepositoryImpl(get(), get()) }
    factory { ProfileRepositoryImpl(get(), get()) }
    factory { MeetingsRepositoryImpl(get(), get(), get<MessagesRepositoryImpl>()) }
}