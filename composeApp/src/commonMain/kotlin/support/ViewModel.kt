package support

import androidx.compose.runtime.Composable
import data.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindSingleton
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.subDI
import kotlin.coroutines.CoroutineContext

abstract class ViewModel : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Default
}

@Composable
inline fun <reified VM : ViewModel> bindViewModel(
    crossinline instantiate: DI.MainBuilder.(DirectDI) -> VM,
    crossinline viewmodelDelegate: @Composable (VM) -> Unit
) {
    subDI(
        parentDI = AppModule,
        diBuilder = {
            bindSingleton { instantiate(this@bindSingleton) }
        }
    ) {
        val viewModel by rememberInstance<VM>()
        viewmodelDelegate(viewModel)
    }
}

