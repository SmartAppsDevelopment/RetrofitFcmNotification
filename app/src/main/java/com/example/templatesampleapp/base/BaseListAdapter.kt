/*
 * Copyright 2019 vmadalin.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.templatesampleapp.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.templatesampleapp.adapter.holders.SampleViewHolder

/**
 * Base list adapter to standardize and simplify initialization for this component.
 *
 * @param itemsSame Function called to check whether two objects represent the same item.
 * @param contentsSame Function called to check whether two items have the same data.
 * @see ListAdapter
 */
abstract class BaseListAdapter<T : BaseModel, H : androidx.databinding.ViewDataBinding, K : BaseViewHolder<T,H>> :
    ListAdapter<T, K>(object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(old: T, new: T): Boolean = old.isItemSame(new)
        override fun areContentsTheSame(old: T, new: T): Boolean = old.isContentSame(new)
    }) {

    /**
     * Called when RecyclerView needs a new [RecyclerView.ViewHolder] of the given type to
     * represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param inflater Instantiates a layout XML file into its corresponding View objects.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see ListAdapter.onCreateViewHolder
     */
    abstract fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): K

    /**
     * Called when RecyclerView needs a new [RecyclerView.ViewHolder] of the given type to
     * represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see ListAdapter.onCreateViewHolder
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): K =
        onCreateViewHolder(
            parent=parent,
            LayoutInflater.from(parent.context),
            viewType
        )


    override fun onBindViewHolder(holder: K, position: Int) =holder.bind(getItem(position))

}
