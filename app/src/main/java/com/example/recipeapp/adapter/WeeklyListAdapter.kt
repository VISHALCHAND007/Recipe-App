package com.example.recipeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.SampleLayoutWeeklyMealPlanBinding
import com.example.recipeapp.room.entity.WeeklyPlanEntity

class WeeklyListAdapter(private val mContext: Context, private val mList: List<WeeklyPlanEntity>) :
    RecyclerView.Adapter<WeeklyListAdapter.WeeklyListViewHolder>() {
    inner class WeeklyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SampleLayoutWeeklyMealPlanBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyListViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.sample_layout_weekly_meal_plan, parent, false)
        return WeeklyListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: WeeklyListViewHolder, position: Int) {
        val item = mList[position]
        holder.binding.recipeName.text = item.mealName
        //applying adapter for ingredients
        holder.binding.ingredientsRv.layoutManager = LinearLayoutManager(mContext)
        holder.binding.ingredientsRv.adapter = IngredientsAdapter(mContext, item.ingredients)
    }
}