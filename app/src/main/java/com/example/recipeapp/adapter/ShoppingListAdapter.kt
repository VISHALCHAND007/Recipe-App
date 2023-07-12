package com.example.recipeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.SampleLayoutShoppingListBinding
import com.example.recipeapp.room.entity.ShoppingListEntity
import com.example.recipeapp.utils.Helper

class ShoppingListAdapter(
    private val mContext: Context,
    private val mList: List<ShoppingListEntity>,
    private val onClickView: OnClickViews
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewModel>() {
    inner class ShoppingListViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SampleLayoutShoppingListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewModel {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.sample_layout_shopping_list, parent, false)
        return ShoppingListViewModel(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ShoppingListViewModel, position: Int) {
        holder.binding.mealNameTv.text = mList[position].mealName
        holder.binding.deleteImg.setOnClickListener {
            Helper().makeToast(mContext, "Added clicked.")
        }
        holder.binding.ingredientsRv.adapter =
            ShoppingListAddIngredientsAdapter(mContext, mList[position].ingredients, object: ShoppingListAddIngredientsAdapter.OnAdded{
                override fun onAddClicked(ingredient: Pair<String, String>) {
                    onClickView.onAddClicked(ingredient)
                }

            })
        holder.binding.ingredientsRv.layoutManager = LinearLayoutManager(mContext)

        //handling clicks
        holder.binding.saveBtn.setOnClickListener {
            onClickView.onSaveBtnClicked(mList[position])
        }
        holder.binding.deleteImg.setOnClickListener {
            onClickView.onDeleteClicked(mList[position].mealName)
        }
    }
    interface OnClickViews {
        fun onDeleteClicked(mealName: String)
        fun onSaveBtnClicked(shoppingListEntity: ShoppingListEntity)
        fun onAddClicked(ingredient: Pair<String, String>)
    }
}