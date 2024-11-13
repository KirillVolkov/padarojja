package screen.main

import data.remote.RemoteClient
import data.repository.CityRepository
import support.ViewModel

class MainViewModel(
    private val remoteClient: RemoteClient,
    private val cityRepository: CityRepository
) : ViewModel() {

    val selectedCity = cityRepository.selectedCity

    suspend fun getPlaces() = selectedCity.value?.let {
        remoteClient.getPlaces(it.id)
    }

    suspend fun getRoutes() = remoteClient.getRoutes()
}
