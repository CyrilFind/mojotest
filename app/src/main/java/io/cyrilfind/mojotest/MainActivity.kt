package io.cyrilfind.mojotest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.cyrilfind.mojotest.databinding.ActivityMainBinding
import io.cyrilfind.mojotest.templater.Templater

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        loadSample1()
        binding.loadButton.setOnClickListener { loadSample1() }
        binding.loadButton2.setOnClickListener { loadSample2() }
    }

    private fun loadSample2() {
        lifecycleScope.launchWhenStarted {
            Templater.get().load(R.raw.sample2).into(binding.mainOverlay)
        }
    }

    private fun loadSample1() {
        lifecycleScope.launchWhenStarted {
            Templater.get().load(R.raw.sample).into(binding.mainOverlay)
        }
    }
}