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
import com.example.recipeapp.databinding.FragmentShoppingListBinding
import com.example.recipeapp.room.entity.ShoppingListEntity
import com.example.recipeapp.room.entity.WeeklyPlanEntity
import com.example.recipeapp.utils.Helper
import com.example.recipeapp.viewModel.ShoppingListViewModel
import com.example.recipeapp.viewModel.WeeklyMealViewModel

class ShoppingListFragment : Fragment() {
    private lateinit var binding: FragmentShoppingListBinding
    private lateinit var mContext: Context
    private lateinit var viewModel: ShoppingListViewModel
    private lateinit var viewModelWeeklyPlan: WeeklyMealViewModel
    private lateinit var adapter: ShoppingListAdapter
    private lateinit var ingredients: ArrayList<Pair<String, String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        initTasks()
        getData()
    }

    private fun initTasks() {
        mContext = requireContext()
        viewModel = ViewModelProvider(requireActivity())[ShoppingListViewModel::class.java]
        viewModelWeeklyPlan = ViewModelProvider(requireActivity())[WeeklyMealViewModel::class.java]
        ingredients = ArrayList()
    }

    private fun getData() {


        binding.shoppingListRv.layoutManager = LinearLayoutManager(requireContext())
        viewModel.allData.observe(viewLifecycleOwner) {
            checkDataExists(it)
            adapter = ShoppingListAdapter(
                requireContext(),
                it,
                object : ShoppingListAdapter.OnClickViews {
                    override fun onDeleteClicked(mealName: String) {
                        viewModel.delete(mealName)
                    }

                    override fun onSaveBtnClicked(shoppingListEntity: ShoppingListEntity) {
                        val itemEntity = WeeklyPlanEntity(
                            shoppingListEntity.mealName,
                            ingredients
                        )
                        viewModel.delete(shoppingListEntity.mealName)
                        viewModelWeeklyPlan.insert(itemEntity)
                        Helper().makeToast(requireContext(), "Saved in Weekly Meal Plan")
                    }

                    override fun onAddClicked(ingredient: Pair<String, String>) {
                        ingredients.add(ingredient)
                    }

                })
            binding.shoppingListRv.adapter = adapter
        }
    }

    private fun checkDataExists(list: List<ShoppingListEntity>) {
        binding.progressRl.visibility = View.GONE

        if (list.isNotEmpty()) {
            binding.noResultFoundTv.visibility = View.GONE
            binding.shoppingListRv.visibility = View.VISIBLE

        } else {
            binding.noResultFoundTv.visibility = View.VISIBLE
            binding.shoppingListRv.visibility = View.GONE
        }
    }
}