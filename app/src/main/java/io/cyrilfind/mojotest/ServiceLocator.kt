package io.cyrilfind.mojotest

import android.content.Context
import io.cyrilfind.mojotest.templater.Templater
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

object ServiceLocator {
    internal lateinit var appContext: Context
    
    private val ioDispatcher = Dispatchers.IO
    private val backgroundDispatcher = Dispatchers.Default

    private val jsonDeserializer by lazy { Json { ignoreUnknownKeys = true } }
    private val imageFetcher: ImageFetcher by lazy { ImageFetcher(appContext, ioDispatcher) }
    
    fun getTemplater(): Templater {
        return Templater(appContext, imageFetcher, jsonDeserializer, backgroundDispatcher, ioDispatcher)
    }
}