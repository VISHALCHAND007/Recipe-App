package com.example.recipeapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.RecipeActivity
import com.example.recipeapp.databinding.SampleLayoutMealBinding
import com.example.recipeapp.models.MealsModel
import com.example.recipeapp.utils.Helper
import java.io.Serializable

class MealsAdapter(
    private val mList: ArrayList<MealsModel>?,
    private val mContext: Context,
    private val isPlanMealSelected: Boolean
) : RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {
    inner class MealsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val binding = SampleLayoutMealBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.sample_layout_meal, parent, false)
        return MealsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        holder.binding.mealNameTv.text = mList?.get(position)?.strMeal
        holder.binding.mealCategory.text = mList?.get(position)?.strCategory
        Glide.with(mContext).load(mList?.get(position)?.strMealThumb).into(holder.binding.recipeImg)

        if (isPlanMealSelected) {
            holder.binding.addTv.visibility = View.VISIBLE
            holder.binding.favouriteImg.visibility = View.GONE
        } else {
            holder.binding.addTv.visibility = View.GONE
            holder.binding.favouriteImg.visibility = View.VISIBLE
        }

        //handling clicks
        holder.binding.individualItemCl.setOnClickListener {
            val ingredients = mList?.get(position)?.ingredients
            val bundle = Bundle()
            val intent = Intent(mContext, RecipeActivity::class.java)
            intent.putExtra(Helper.MEAL_NAME, mList?.get(position)?.strMeal)
            intent.putExtra(Helper.MEAL_IMG, mList?.get(position)?.strMealThumb)
            intent.putExtra(Helper.MEAL_RECIPE, mList?.get(position)?.strInstructions)
            bundle.putSerializable(Helper.MEAL_INGREDIENTS,  ingredients as Serializable)
            intent.putExtra(Helper.BUNDLE, bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            mContext.startActivity(intent)
        }
    }
}