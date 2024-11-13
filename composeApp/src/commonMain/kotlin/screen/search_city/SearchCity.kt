package screen.search_city

import ColorTable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.kodein.di.instance
import support.bindViewModel
import vandrouka.composeapp.generated.resources.Res
import vandrouka.composeapp.generated.resources.ic_carret_right

@Composable
internal fun SearchCity(onClose: () -> Unit) {
    bindViewModel({ SearchCityViewModel(it.instance()) }) { viewModel ->

        val coroutineScope = rememberCoroutineScope()

        val cities by viewModel.citiesFlow.collectAsState()

        val selected by viewModel.selectedCity.collectAsState()

        LazyColumn(
            modifier = Modifier.background(Color.White)
                .windowInsetsPadding(WindowInsets.navigationBars),
            contentPadding = PaddingValues(top = 32.dp)
        ) {
            items(cities) {
                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().clickable {
                    viewModel.selectCity(it)
                    coroutineScope.launch {
                        delay(300)
                        onClose()
                    }
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            it.name,
                            modifier = Modifier.weight(1f).padding(16.dp),
                            color = if (it.id == selected?.id) Color.Blue else Color.Black
                        )
                        Icon(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            painter = painterResource(Res.drawable.ic_carret_right),
                            contentDescription = null,
                            tint = ColorTable.textGrey.copy(alpha = .3f)
                        )
                    }
                    Divider(
                        modifier = Modifier.fillMaxWidth().height(1.dp),
                        color = ColorTable.textGrey.copy(alpha = .3f)
                    )
                }
            }
        }
    }
}
