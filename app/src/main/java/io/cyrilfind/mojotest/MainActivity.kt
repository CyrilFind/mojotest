package io.cyrilfind.mojotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.cyrilfind.mojotest.databinding.ActivityMainBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // read raw file
        val raw = resources.openRawResource(R.raw.sample).bufferedReader().use { it.readText() }
        
        // parse json
        val overlay = Json.decodeFromString<Overlay>(raw)
        
        binding.mainTextView.text = overlay.toString()
    }
}