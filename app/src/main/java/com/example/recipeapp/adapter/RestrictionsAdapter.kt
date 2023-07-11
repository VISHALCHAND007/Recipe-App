package com.example.recipeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R

class RestrictionsAdapter(private val mContext: Context?, private val list: ArrayList<String>): RecyclerView.Adapter<RestrictionsAdapter.RestrictionsViewHolder>() {
    inner class RestrictionsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestrictionsViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.sample_layout_restrictions, parent, false)
        return RestrictionsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RestrictionsViewHolder, position: Int) {
//        holder.textView.text = list[position].checkListItem
//        holder.deleteImg.setOnClickListener {
//            list.removeAt(position)
//            notifyDataSetChanged()
//        }
    }
}