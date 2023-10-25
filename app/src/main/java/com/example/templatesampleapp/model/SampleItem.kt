package com.example.templatesampleapp.model

import com.example.templatesampleapp.base.BaseModel
import com.example.templatesampleapp.base.ListItemViewModel

 class SampleItem(var name:String, var id:Int
 ) : BaseModel(),ListItemViewModel{
     override var adapterPosition: Int = 0
     override lateinit var onListItemViewClickListener: (BaseModel, Int) -> Unit?
 }