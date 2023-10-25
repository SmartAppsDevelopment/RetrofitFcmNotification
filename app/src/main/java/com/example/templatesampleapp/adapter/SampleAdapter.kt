package com.example.templatesampleapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.templatesampleapp.R
import com.example.templatesampleapp.adapter.holders.SampleViewHolder
import com.example.templatesampleapp.base.BaseListAdapter
import com.example.templatesampleapp.databinding.SampleItemBinding
import com.example.templatesampleapp.model.SampleItem

/**
 * This is only for Sample to Show
 * How to impl Generic Adapter in whole application
 * you may delete it when Impl this template in your project
 */
class SampleAdapter : BaseListAdapter<SampleItem, SampleItemBinding, SampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,inflater: LayoutInflater, viewType: Int): SampleViewHolder {
        val binding=
            DataBindingUtil.inflate<SampleItemBinding>(inflater, R.layout.sample_item,parent,false)
       // val binding=SampleItemBinding.inflate(inflater, parent, false)
        return SampleViewHolder(binding)
    }

}