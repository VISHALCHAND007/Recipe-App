package com.example.recipeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.SampleLayoutAddIngredientsBinding

class ShoppingListAddIngredientsAdapter(
    private val mContext: Context,
    private val mList: ArrayList<Pair<String, String>>,
    private val onAdded: OnAdded
) : RecyclerView.Adapter<ShoppingListAddIngredientsAdapter.ShoppingListAddViewHolder>() {
    inner class ShoppingListAddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SampleLayoutAddIngredientsBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListAddViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.sample_layout_add_ingredients, parent, false)
        return ShoppingListAddViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ShoppingListAddViewHolder, position: Int) {
        val item = mList[position]
        holder.binding.ingredientTv.text = item.first
        holder.binding.addImg.setOnClickListener {
            onAdded.onAddClicked(item)
            holder.binding.addImg.setImageResource(R.drawable.check)
        }
    }

    interface OnAdded {
        fun onAddClicked(ingredient: Pair<String, String>)
    }
}