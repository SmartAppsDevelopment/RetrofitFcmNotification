package com.example.templatesampleapp.base


abstract class BaseModel {
    fun isItemSame(objects: BaseModel): Boolean {
        return objects.hashCode() == hashCode()
    }

    fun isContentSame(objects: BaseModel): Boolean {
        return this == objects
    }
}