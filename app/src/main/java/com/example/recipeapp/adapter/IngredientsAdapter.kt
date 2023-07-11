package com.example.recipeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.SampleLayoutTableRowBinding

class IngredientsAdapter(private val mContext: Context, private val mList: List<Pair<String, String>>):RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {
    inner class IngredientsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = SampleLayoutTableRowBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.sample_layout_table_row, parent, false)
        return IngredientsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val item = mList[position]
        if(item.first != "null" && item.second != "null") {
            holder.binding.ingredientTv.text = item.first
            holder.binding.quantityTv.text = "-> ${item.second}"
        }
    }
}