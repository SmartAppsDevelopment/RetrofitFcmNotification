package com.example.templatesampleapp.bindings

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.templatesampleapp.adapter.SampleAdapter
import com.example.templatesampleapp.base.BaseModel
import com.example.templatesampleapp.model.SampleItem


@BindingAdapter( "list")
fun RecyclerView.init(itemList: MutableLiveData<List<SampleItem>>? ) {
//    (adapter as SampleAdapter).submitList(itemList)
    adapter = SampleAdapter().apply {
        submitList(listOf(
            SampleItem("B", 1),
            SampleItem("C", 2),
            SampleItem("D", 3),
            SampleItem("E", 4)
        ))

    }
}