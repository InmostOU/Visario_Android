package pro.inmost.android.visario.di.modules

import org.koin.dsl.module
import pro.inmost.android.visario.data.repositories.*

val repositories = module {
    factory { AccountRepositoryImpl(get(), get()) }
    factory { ChannelsRepositoryImpl(get(), get(), get<MessagesRepositoryImpl>()) }
    factory { MessagesRepositoryImpl(get(), get(), get<ProfileRepositoryImpl>()) }
    factory { ContactsRepositoryImpl(get(), get()) }
    factory { ProfileRepositoryImpl(get(), get()) }
    factory { MeetingsRepositoryImpl(get(), get<MessagesRepositoryImpl>(), get(), get(), get()) }
}