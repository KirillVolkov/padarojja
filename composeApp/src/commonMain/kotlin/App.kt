import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import navigation.RootNav
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme() {

        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }

        RootNav()
    }
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).crossfade(true)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
        }
        .logger(DebugLogger())
        .fetcherCoroutineContext(Dispatchers.IO)
        .build()


