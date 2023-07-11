package com.example.recipeapp.adapter

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
import com.example.recipeapp.models.MealsModel
import com.example.recipeapp.room.FavouriteDatabase
import com.example.recipeapp.room.FavouriteEntity
import com.example.recipeapp.utils.Helper

class MealsAdapter(
    private val mList: ArrayList<MealsModel>?,
    private val mContext: Context,
    private val isPlanMealSelected: Boolean,
    private val onClick: OnClick
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
        val item = mList?.get(position)
        holder.binding.mealNameTv.text = item?.strMeal
        holder.binding.mealCategory.text = item?.strCategory
        Glide.with(mContext).load(item?.strMealThumb).into(holder.binding.recipeImg)

        if (isPlanMealSelected) {
            holder.binding.addTv.visibility = View.VISIBLE
            holder.binding.favouriteImg.visibility = View.GONE
        } else {
            holder.binding.addTv.visibility = View.GONE
            holder.binding.favouriteImg.visibility = View.VISIBLE
        }
//        val isPresent = FavouriteDatabase.getDatabaseInstance(mContext).favouriteDao().checkExist(item!!.idMeal)
//        //setting heart
//        if(isPresent.isNotEmpty()) {
//            holder.binding.favouriteImg.setImageResource(R.drawable.heart)
//        } else {
//            holder.binding.favouriteImg.setImageResource(R.drawable.heart_outlined)
//        }

        //handling clicks
        holder.binding.individualItemCl.setOnClickListener {
            val intent = Intent(mContext, RecipeActivity::class.java)
            intent.putExtra(Helper.MEAL_NAME, item!!.strMeal)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(intent)
        }

        holder.binding.favouriteImg.setOnClickListener {

            val favouriteEntity = FavouriteEntity(
                item!!.idMeal,
                item.strMeal,
                item.strCategory,
                item.strMealThumb,
                true
            )
            onClick.onFavouriteClick(favouriteEntity)
        }
    }
    interface OnClick {
        fun onFavouriteClick(favouriteEntity: FavouriteEntity)
        fun onAddClick()
    }
}