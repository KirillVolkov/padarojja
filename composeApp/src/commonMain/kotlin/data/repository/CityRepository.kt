package data.repository

import data.remote.RemoteClient
import data.remote.model.City
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CityRepository(private val remoteClient: RemoteClient) : CoroutineScope {

    var selectedCity: MutableStateFlow<City?> = MutableStateFlow(null)

    val cities: MutableStateFlow<List<City>> = MutableStateFlow(emptyList())

    suspend fun getCities() = remoteClient.getCities()

    init {
        launch {
            cities.value = getCities().also {
                selectedCity.value = it.minByOrNull { it.id }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO
}
