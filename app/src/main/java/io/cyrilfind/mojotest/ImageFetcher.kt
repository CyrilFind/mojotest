package io.cyrilfind.mojotest

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ImageFetcher(
    context: Context,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    private val requestManager: RequestManager = Glide.with(context)
    suspend fun fetch(url: String): Bitmap = withContext(coroutineDispatcher) {
        requestManager.asBitmap().load(url).submit().get()
    }
}