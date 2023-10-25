package com.example.templatesampleapp.base

interface ListItemViewModel{
    var adapterPosition: Int
    var onListItemViewClickListener: (BaseModel,Int)->Unit?
}