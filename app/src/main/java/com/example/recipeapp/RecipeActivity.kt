package com.example.recipeapp

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.adapter.IngredientsAdapter
import com.example.recipeapp.databinding.ActivityRecipeBinding
import com.example.recipeapp.utils.Helper
import com.google.android.material.tabs.TabLayout

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private lateinit var mealImg: String
    private lateinit var mealName: String
    private lateinit var mealRecipe: String
    private lateinit var ingredientsList: List<Pair<String, String>>
    private var isRecipeTabSelected: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init() {
        initTasks()
        initListeners()
    }

    private fun initTasks() {
        val intent = intent.extras
        if(intent != null) {
            mealImg = intent.getString(Helper.MEAL_IMG, "")
            mealName = intent.getString(Helper.MEAL_NAME, "")
            mealRecipe = intent.getString(Helper.MEAL_RECIPE, "")
            val bundle = intent.getBundle(Helper.BUNDLE)
            ingredientsList = bundle?.getSerializable(Helper.MEAL_INGREDIENTS) as List<Pair<String, String>>

            setData()
        } else {
            Helper().makeToast(this@RecipeActivity, resources.getString(R.string.error))
            finish()
        }
        //initialization
        ingredientsList = ArrayList()
    }

    private fun setData() {
        binding.recipeName.text = mealName
        Glide.with(this@RecipeActivity).load(mealImg).into(binding.recipeImg)
        setIngredients()
        binding.recipeTv.text = mealRecipe
        Handler(mainLooper).postDelayed({
            binding.progressRl.visibility = View.GONE
        },2000)
    }

    private fun setIngredients() {
        val adapter = IngredientsAdapter(this@RecipeActivity, ingredientsList)
        binding.ingredientsRv.layoutManager = LinearLayoutManager(this@RecipeActivity)
        binding.ingredientsRv.adapter = adapter
    }

    private fun initListeners() {
        binding.tabLayout.setOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.position == 0) {
                    isRecipeTabSelected = false
                    binding.tabLayout.getTabAt(0)?.select()
                    binding.tableIngredients.visibility = View.VISIBLE
                    binding.recipeTv.visibility = View.GONE
                    binding.ingredientsRv.visibility = View.VISIBLE
                } else {
                    isRecipeTabSelected = true
                    binding.tabLayout.getTabAt(1)?.select()
                    binding.tableIngredients.visibility = View.GONE
                    binding.recipeTv.visibility = View.VISIBLE
                    binding.ingredientsRv.visibility = View.GONE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        binding.backArrowIv.setOnClickListener {
            onBackPressed()
        }
    }
}