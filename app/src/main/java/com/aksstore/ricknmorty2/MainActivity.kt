package com.aksstore.ricknmorty2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.withStarted
import com.aksstore.ricknmorty2.adapter.RickNMortyAdapter
import com.aksstore.ricknmorty2.databinding.ActivityMainBinding
import com.aksstore.ricknmorty2.viewmodel.RickNMortyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: RickNMortyViewModel by viewModels()
    lateinit var adapter: RickNMortyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = RickNMortyAdapter(this)
        binding.rvRNM.adapter = adapter
        setObserver()
//        binding.btnCheck.setOnClickListener {
//            viewModel.getCharacterById(2)
//        }
        viewModel.getCharacterById(2)
    }

    private fun setObserver() {

        with(adapter) {
            lifecycleScope.launch {
                // repeatOnLifecycle launches the block in a new coroutine every time the
                // lifecycle is in the STARTED state and cancels it when it's STOPPED.
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // Start listening for values.
                    // This happens when lifecycle is STARTED and stops
                    // collecting when the lifecycle is STOPPED
                    viewModel.rnmFlow.collect {
                        submitData(it)
                    }
                }
            }

            viewModel.errorMessage.observe(this@MainActivity) {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val rnmResult = viewModel.rnmResultStateFlow.collect {
                    Log.d("TAG", "setObserver: " + it)
                }
            }

        }

    }
}