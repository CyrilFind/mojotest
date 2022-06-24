package io.cyrilfind.mojotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.cyrilfind.mojotest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // read raw file
        val raw = resources.openRawResource(R.raw.sample).bufferedReader().use { it.readText() }
        
        binding.mainTextView.text = raw
        
    }
}