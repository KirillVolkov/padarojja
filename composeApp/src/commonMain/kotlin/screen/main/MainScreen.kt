package screen.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import data.remote.model.Place
import data.remote.model.Route
import navigation.BottomSheetType
import org.kodein.di.instance
import support.bindViewModel
import vandrouka.composeapp.generated.resources.Res
import vandrouka.composeapp.generated.resources.category_places
import vandrouka.composeapp.generated.resources.category_routes

@Composable
fun MainScreen(onNavigate: (String) -> Unit = {}, bottomSheetType: (BottomSheetType) -> Unit) {

    bindViewModel({ MainViewModel(it.instance(), it.instance()) }) { viewModel ->

        val city by viewModel.selectedCity.collectAsState()

        val places by produceState<List<Place>?>(key1 = city, initialValue = null) {
            value = viewModel.getPlaces()
        }

        val routes by produceState<List<Route>?>(null) {
            value = viewModel.getRoutes()
        }

        val categories = mutableListOf<Category<*>>()

        routes?.let {
            categories.add(0, Category.Routes(Res.string.category_routes, it))
        }

        places?.let {
            categories.add(Category.Places(Res.string.category_places, it))
        }
        MainList(categories) {
            Header(city = city, callSearchCity = {
                bottomSheetType(BottomSheetType.SearchCity)
            })
        }
    }
}

