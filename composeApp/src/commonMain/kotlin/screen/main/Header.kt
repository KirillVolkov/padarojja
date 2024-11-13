package screen.main

import ColorTable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.remote.model.City
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import vandrouka.composeapp.generated.resources.Res
import vandrouka.composeapp.generated.resources.find_place
import vandrouka.composeapp.generated.resources.go_to_title
import vandrouka.composeapp.generated.resources.ic_dropdown
import vandrouka.composeapp.generated.resources.ic_search

@Preview
@Composable
internal fun Header(city: City?, callSearchCity: () -> Unit) {
    Column(
        modifier = Modifier
            .background(ColorTable.mainPurple)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                stringResource(Res.string.go_to_title),
                fontSize = 22.sp,
                color = Color.White.copy(alpha = .5f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    callSearchCity()
                }) {
                Text(
                    city?.name ?: "...",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painterResource(Res.drawable.ic_dropdown),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            SearchInput()
            Spacer(Modifier.height(24.dp))
        }
        Box(
            modifier = Modifier.fillMaxWidth().height(24.dp).background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
        )
    }
}

@Composable
private fun SearchInput() {

    var text by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(Res.drawable.ic_search),
            contentDescription = null,
            tint = ColorTable.textGrey
        )
        TextField(
            shape = RectangleShape,
            placeholder = {
                Text(
                    stringResource(Res.string.find_place),
                    fontSize = 14.sp,
                    color = ColorTable.textGrey
                )
            },
            maxLines = 1,
            value = text,
            onValueChange = {
                text = it
            }, modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            colors = TextFieldDefaults.textFieldColors(
                textColor = ColorTable.textGrey,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
        )
    }
}
