package data

import data.remote.RemoteClient
import data.repository.CityRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val AppModule = DI {
    bindSingleton { RemoteClient() }
    bindSingleton { CityRepository(instance()) }
}
