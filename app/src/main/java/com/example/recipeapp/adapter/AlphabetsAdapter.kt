package com.example.recipeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.SampleLayoutAlphabetBinding

class AlphabetsAdapter(
    private val mContext: Context,
    private val mList: ArrayList<String>,
    private var selectedChar: String,
    private val onClick: OnClick
) :
    RecyclerView.Adapter<AlphabetsAdapter.AlphabetsViewHolder>() {
    inner class AlphabetsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SampleLayoutAlphabetBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetsViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.sample_layout_alphabet, parent, false)
        return AlphabetsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: AlphabetsViewHolder, position: Int) {
        holder.binding.alphabetTv.text = mList[position]

        if(selectedChar == mList[position]) {
            holder.binding.alphabetTv.background = ContextCompat.getDrawable(mContext, R.drawable.alphabet_selected)
        } else {
            holder.binding.alphabetTv.background = ContextCompat.getDrawable(mContext, R.drawable.alphabet_deselected)
        }

        //setting up on Click listener
        holder.binding.alphabetTv.setOnClickListener {
            onClick.onAlphabetClicked(mList[position])
            selectedChar =  mList[position]
        }
    }
    interface OnClick {
        fun onAlphabetClicked(alphabet: String)
    }
}