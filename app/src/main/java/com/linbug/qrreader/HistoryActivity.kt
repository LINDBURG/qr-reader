package com.linbug.qrreader

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.linbug.qrreader.databinding.ActivityHistoryBinding

class HistoryActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val urlViewModel: UrlViewModel by viewModels {
        UrlViewModelFactory((application as UrlsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = UrlListAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        urlViewModel.allUrls.observe(this) { urls ->
            // Update the cached copy of the words in the adapter.
            urls?.let { adapter.submitList(it) }
        }
    }


}