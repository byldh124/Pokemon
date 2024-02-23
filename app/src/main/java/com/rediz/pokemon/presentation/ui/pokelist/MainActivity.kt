package com.rediz.pokemon.presentation.ui.pokelist

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rediz.pokemon.R
import com.rediz.pokemon.common.collectEvent
import com.rediz.pokemon.common.viewBinding
import com.rediz.pokemon.databinding.ActivityMainBinding
import com.rediz.pokemon.presentation.base.BaseActivity
import com.rediz.pokemon.presentation.base.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModel<MainViewModel>()
    private val adapter = PokeListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        collectEvent(viewModel.eventFlow, ::handleEvent)

        binding.recycler.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        binding.recycler.adapter = adapter

        viewModel.getList()
    }

    private fun handleEvent(event: MainViewModel.Event) {
        when (event) {
            is MainViewModel.Event.Update -> {
                lifecycleScope.launch {
                    adapter.submitData(event.data)
                }
            }
        }
    }

}