package com.example.recipeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.RecipeActivity
import com.example.recipeapp.databinding.SampleLayoutMealBinding
import com.example.recipeapp.room.FavouriteEntity
import com.example.recipeapp.utils.Helper

class FavouriteAdapter(
    private val mContext: Context,
    private val mList: List<FavouriteEntity>,
    private val onClick: OnClick
) : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {
    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SampleLayoutMealBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.sample_layout_meal, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val item = mList[position]
        holder.binding.mealNameTv.text = item.recipeName
        holder.binding.mealCategory.text = item.recipeCategory
        Glide.with(mContext).load(item.recipeImg).into(holder.binding.recipeImg)

        holder.binding.addTv.visibility = View.GONE
        holder.binding.favouriteImg.visibility = View.VISIBLE

        val isPresent = item.isFavourite
        //setting heart
        if (isPresent) {
            holder.binding.favouriteImg.setImageResource(R.drawable.heart)
        } else {
            holder.binding.favouriteImg.setImageResource(R.drawable.heart_outlined)
        }

        //handling clicks
        holder.binding.individualItemCl.setOnClickListener {
            val intent = Intent(mContext, RecipeActivity::class.java)
            intent.putExtra(Helper.MEAL_NAME, item.recipeName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(intent)
        }

        holder.binding.favouriteImg.setOnClickListener {
            onClick.onFavouriteImgClick(item)
        }
    }
    interface OnClick {
        fun onFavouriteImgClick(favouriteEntity: FavouriteEntity)
    }
}