package screen.search_city

import data.remote.model.City
import data.repository.CityRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import support.ViewModel

class SearchCityViewModel(private val cityRepository: CityRepository) : ViewModel() {

    val citiesFlow = cityRepository.cities

    val selectedCity = cityRepository.selectedCity

    fun selectCity(city: City) {
        cityRepository.selectedCity.value = city
    }

}
