package com.example.recipeapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.adapter.ShoppingListAdapter
import com.example.recipeapp.adapter.WeeklyListAdapter
import com.example.recipeapp.databinding.FragmentMealPlanBinding
import com.example.recipeapp.room.entity.WeeklyPlanEntity
import com.example.recipeapp.viewModel.WeeklyMealViewModel

class MealPlanFragment : Fragment() {
    private lateinit var binding: FragmentMealPlanBinding
    private lateinit var mContext: Context
    private lateinit var viewModel: WeeklyMealViewModel
    private lateinit var adapter: WeeklyListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMealPlanBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        initTasks()
        getData()
    }

    private fun initTasks() {
        mContext = requireContext()
        viewModel = ViewModelProvider(requireActivity())[WeeklyMealViewModel::class.java]
    }

    private fun getData() {


        binding.weeklyShoppingListRv.layoutManager = LinearLayoutManager(requireContext())
        viewModel.allData.observe(viewLifecycleOwner) {
            checkDataExists(it)
            adapter = WeeklyListAdapter(requireContext(), it)
            binding.weeklyShoppingListRv.adapter = adapter
        }
    }

    private fun checkDataExists(list: List<WeeklyPlanEntity>) {
        binding.progressRl.visibility = View.GONE

        if (list.isNotEmpty()) {
            binding.noResultFoundTv.visibility = View.GONE
            binding.weeklyShoppingListRv.visibility = View.VISIBLE

        } else {
            binding.noResultFoundTv.visibility = View.VISIBLE
            binding.weeklyShoppingListRv.visibility = View.GONE
        }
    }
}