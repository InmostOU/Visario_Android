package pro.inmost.android.visario.di.modules

import org.koin.dsl.module
import pro.inmost.android.visario.data.database.AppDatabase

/**
 * Room's DAOs module for Koin dependency framework
 */
val dao = module {
    factory { (get() as AppDatabase).channelsDao() }
    factory { (get() as AppDatabase).messagesDao() }
    factory { (get() as AppDatabase).contactsDao() }
    factory { (get() as AppDatabase).profileDao() }
    factory { (get() as AppDatabase).attendeesDao() }
}