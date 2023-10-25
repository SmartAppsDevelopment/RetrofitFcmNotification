package com.example.templatesampleapp.ui.activmain

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.templatesampleapp.R
import com.example.templatesampleapp.adapter.SampleAdapter
import com.example.templatesampleapp.base.BaseActivity
import com.example.templatesampleapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.rvItemList.adapter = SampleAdapter().apply {
//            submitList(viewModel.getData().value)
//        }
    }
}