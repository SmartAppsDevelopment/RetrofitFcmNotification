package com.example.templatesampleapp.adapter.holders

import com.example.templatesampleapp.base.BaseViewHolder
import com.example.templatesampleapp.databinding.SampleItemBinding
import com.example.templatesampleapp.model.SampleItem

open class SampleViewHolder(binding: SampleItemBinding):BaseViewHolder<SampleItem,SampleItemBinding>(binding){
    override fun bind(model: SampleItem) {
        binding.model= model
       /// binding.setVariable()
        binding.executePendingBindings()
    }
}