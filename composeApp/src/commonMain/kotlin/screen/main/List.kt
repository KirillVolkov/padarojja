package screen.main

import ColorTable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import data.remote.model.Place
import data.remote.model.Route
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import support.roundToDecimals
import vandrouka.composeapp.generated.resources.Res
import vandrouka.composeapp.generated.resources.ic_category_places
import vandrouka.composeapp.generated.resources.ic_star
import kotlin.random.Random

sealed class Category<T>(val name: StringResource, val list: List<T>) {
    class Routes(name: StringResource, list: List<Route>) : Category<Route>(name, list)
    class Places(name: StringResource, list: List<Place>) : Category<Place>(name, list)
}

@Composable
fun MainList(categories: List<Category<*>>, header: @Composable LazyItemScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color = Color.White)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        item { header() }
        items(categories) {
            when (it) {
                is Category.Places -> PlacesCategory(it)
                is Category.Routes -> RoutesCategory(it)
            }
        }
    }
}

@Composable
private fun RoutesCategory(routesCategory: Category.Routes) {
    CategoryHeader(routesCategory.name)
    LazyRow {
        items(routesCategory.list) {
            CategoryListItem(
                imageUrl = it.getImageUrl(it.media.firstOrNull()),
                title = it.name,
                subtitle = it.description,
                rating = it.rating.roundToDecimals(1),
                durationHours = it.duration.roundToDecimals(1),
                distance = it.distance.roundToDecimals(1)
            )
        }

    }
}

@Composable
private fun PlacesCategory(placeCategory: Category.Places) {
    CategoryHeader(placeCategory.name)
    LazyRow {
        items(placeCategory.list) {
            CategoryListItem(
                imageUrl = it.getImageUrl(it.media.firstOrNull()),
                title = it.name,
                subtitle = it.description,
                rating = it.rating,
                durationHours = Random.nextDouble(2.0, 5.0).roundToDecimals(1),
                distance = Random.nextDouble(10.0, 250.0).roundToDecimals(1)
            )
        }

    }
}

@Composable
private fun CategoryHeader(name: StringResource) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(Res.drawable.ic_category_places),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                stringResource(name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CategoryListItem(
    imageUrl: String?,
    title: String,
    subtitle: String,
    rating: Double,
    durationHours: Double,
    distance: Double
) {
    Card(
        onClick = {
            //TODO
        },
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .height(300.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {

            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                subtitle,
                fontSize = 14.sp,
                color = ColorTable.textGrey, maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(Res.drawable.ic_star), contentDescription = null)
                Spacer(Modifier.width(2.dp))
                Text(rating.toString(), color = Color.Black)
                Spacer(Modifier.width(8.dp))
                Text("${durationHours.toInt()} ч. ", color = ColorTable.textGrey)
                CenterGreyDot()
                Spacer(Modifier.width(4.dp))
                Text("${distance.toInt()} км. ", color = ColorTable.textGrey)
            }
        }
    }
}

@Composable
private fun CenterGreyDot() = Text("•", color = ColorTable.textGrey)
