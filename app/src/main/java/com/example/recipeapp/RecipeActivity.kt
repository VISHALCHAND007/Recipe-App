package com.example.recipeapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.adapter.IngredientsAdapter
import com.example.recipeapp.databinding.ActivityRecipeBinding
import com.example.recipeapp.utils.Helper
import com.example.recipeapp.utils.InternetManager
import com.example.recipeapp.utils.Url
import com.example.recipeapp.utils.VolleyRequests
import com.google.android.material.tabs.TabLayout
import org.json.JSONObject

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private lateinit var mealName: String
    private lateinit var mealImg: String
    private lateinit var mealRecipe: String
    private lateinit var ingredientsList: ArrayList<Pair<String, String>>
    private var isRecipeTabSelected: Boolean = false
    private lateinit var volleyRequests: VolleyRequests

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
        //initialization
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ingredientsList = ArrayList()
        volleyRequests = VolleyRequests()

        //get intent values
        val intent = intent.extras
        if(intent != null) {
            mealName = intent.getString(Helper.MEAL_NAME, "")
            getData()
        } else {
            Helper().makeToast(this@RecipeActivity, resources.getString(R.string.error))
            finish()
        }

    }

    private fun getData() {
        if(mealName.isNotEmpty() && mealName != " ") {
            if(InternetManager().checkInternet(this@RecipeActivity)) {
                volleyRequests.makeGetRequest(this@RecipeActivity, "${Url.MEAL_SEARCH}$mealName")
                volleyRequests.setVolleyRequest(object: VolleyRequests.VolleyRequestsListener{
                    override fun onDataLoaded(jsonObject: JSONObject) {
                        if(jsonObject.has("meals")) {
                            if(jsonObject.getString("meals").isNotEmpty()) {
                                val json = jsonObject.getJSONArray("meals").getJSONObject(0)
                                mealImg = json.getString("strMealThumb")
                                mealRecipe = json.getString("strInstructions")

                                for (j in 1..20) {
                                    val ingredient = json.getString("strIngredient$j")
                                    val measure = json.getString("strMeasure$j")
                                    if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                                        ingredientsList.add(Pair(ingredient, measure))
                                    }
                                }
                                binding.progressRl.visibility = View.GONE
                                setData()
                            }
                        }
                    }

                    override fun onError(e: Exception) {

                    }

                })
            } else {
                InternetManager().showAlertDialog(this@RecipeActivity)
            }
        }
    }

    private fun setData() {
        binding.recipeName.text = mealName
        Glide.with(this@RecipeActivity).load(mealImg).into(binding.recipeImg)
        setIngredients()
        binding.recipeTv.text = mealRecipe
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