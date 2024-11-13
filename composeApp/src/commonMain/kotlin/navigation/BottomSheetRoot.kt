package navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import screen.search_city.SearchCity

sealed class BottomSheetType {
    data object Hidden : BottomSheetType()
    data object SearchCity : BottomSheetType()
}

@Composable
fun BottomSheetRoot(content: @Composable (bottomSheetType: (BottomSheetType) -> Unit) -> Unit) {

    var bottomSheetType: BottomSheetType by remember { mutableStateOf(BottomSheetType.Hidden) }

    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                coroutineScope.launch(Dispatchers.Default) {
                    delay(300)
                    bottomSheetType = BottomSheetType.Hidden
                }
            }
            true
        },
        skipHalfExpanded = true,
    )

    LaunchedEffect(bottomSheetType) {
        if (bottomSheetType == BottomSheetType.Hidden) {
            modalSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(20.dp),
        sheetElevation = 20.dp,
        sheetContent = {
            when (bottomSheetType) {

                BottomSheetType.SearchCity -> {
                    SearchCity {
                        coroutineScope.launch {
                            modalSheetState.hide()
                        }
                    }
                    LaunchedEffect(Unit) { modalSheetState.show() }
                }

                BottomSheetType.Hidden -> coroutineScope.launch { modalSheetState.hide() }
            }

        },
        content = {
            content {
                bottomSheetType = it
            }
        },
        sheetState = modalSheetState,
    )
}
