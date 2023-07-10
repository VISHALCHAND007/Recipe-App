package com.example.recipeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.SampleLayoutMealBinding

class MealsAdapter(
    private val mContext: Context,
    private val mList: ArrayList<String>,
    private val isPlanMealSelected: Boolean
) : RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {
    inner class MealsViewHolder(itemView: View, private val isPlanMealSelected: Boolean) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
            if (isPlanMealSelected) {
                val binding = SampleLayoutMealBinding.bind(itemView)
            } else {
                val binding = SampleLayoutMealBinding.bind(itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val view: View = if(!isPlanMealSelected){
            LayoutInflater.from(mContext).inflate(R.layout.sample_layout_meal, parent, false)
        } else {
            LayoutInflater.from(mContext).inflate(R.layout.sample_layout_plan_meel, parent, false)
        }
        return MealsViewHolder(view, isPlanMealSelected)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        if (isPlanMealSelected) {

        } else {

        }
    }
}