package com.example.recipeapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.adapter.MealsAdapter
import com.example.recipeapp.databinding.ActivitySearchBinding
import com.example.recipeapp.models.MealsModel
import com.example.recipeapp.room.entity.FavouriteEntity
import com.example.recipeapp.room.entity.ShoppingListEntity
import com.example.recipeapp.utils.Url
import com.example.recipeapp.utils.VolleyRequests
import com.example.recipeapp.viewModel.FavouriteViewModel
import com.example.recipeapp.viewModel.ShoppingListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var volleyRequests: VolleyRequests
    private lateinit var mList: ArrayList<MealsModel>
    private lateinit var mealsAdapter: MealsAdapter
    lateinit var viewModel: FavouriteViewModel
    private lateinit var viewModelShopping: ShoppingListViewModel
    private lateinit var searchWord: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initTasks()
        initListeners()
    }

    private fun initTasks() {
        binding.searchEt.requestFocus()
        searchWord = "a"
        volleyRequests = VolleyRequests()
        viewModel = ViewModelProvider(this@SearchActivity)[FavouriteViewModel::class.java]
        viewModelShopping =
            ViewModelProvider(this@SearchActivity)[ShoppingListViewModel::class.java]
        mList = ArrayList()
        getData(searchWord)
    }

    private fun getData(searchWord: String) {
        mList.clear()
        volleyRequests.makeGetRequest(this@SearchActivity, Url.MEAL_SEARCH + searchWord)
        volleyRequests.setVolleyRequest(object : VolleyRequests.VolleyRequestsListener {
            override fun onDataLoaded(jsonObject: JSONObject) {
                if (jsonObject.has("meals") && !jsonObject.get("meals").equals(null)) {
                    if (jsonObject.getJSONArray("meals").length() > 0) {
                        for (i in 0 until jsonObject.getJSONArray("meals").length()) {
                            val jsonObj = jsonObject.getJSONArray("meals").getJSONObject(i)

                            val idMeal = jsonObj.getString("idMeal")
                            val strMeal = jsonObj.getString("strMeal")
                            jsonObj.getString("strArea")
                            val strCategory = jsonObj.getString("strCategory")
                            val strMealThumb = jsonObj.getString("strMealThumb")

                            // Access the instructions separately since it is a multiline string
                            val strInstructions = jsonObj.getString("strInstructions")

                            // Create an Ingredients list
                            val ingredients = mutableListOf<Pair<String, String>>()
                            for (j in 1..20) {
                                if (jsonObj.get("strIngredient$j") != "null" && jsonObj.getString("strMeasure$j") != "null") {
                                    val ingredient = jsonObj.getString("strIngredient$j")
                                    val measure = jsonObj.getString("strMeasure$j")
                                    if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                                        ingredients.add(Pair(ingredient, measure))
                                    }
                                }
                            }
                            var exists = false
                            var isAdded = false
                            val job = lifecycleScope.launch(Dispatchers.Default) {
                                exists = checkExist(idMeal)
                                isAdded = checkAdded(strMeal)

                            }
                            runBlocking {
                                job.join()
                            }


                            // Create the MealsModel object
                            val mealsModel = MealsModel(
                                idMeal,
                                strCategory,
                                strInstructions,
                                strMeal,
                                strMealThumb,
                                ingredients,
                                exists,
                                isAdded
                            )
                            mList.add(mealsModel)
                        }
                    }
                }
                if (mList.size > 0) {
                    binding.searchRv.visibility = View.VISIBLE
                    setData()
                }
            }

            override fun onError(e: Exception) {

            }

        })
    }

    private fun setData() {
        mealsAdapter =
            MealsAdapter(mList, this@SearchActivity, false, object : MealsAdapter.OnClick {
                override fun onFavouriteClick(favouriteEntity: FavouriteEntity) {
                    val job = lifecycleScope.launch(Dispatchers.IO) {

                        val isPresent = viewModel.checkExist(favouriteEntity.recipeId)
                        if (!isPresent) {
                            //insert fav
                            viewModel.insert(favouriteEntity)
                        } else {
                            //remove fav
                            viewModel.delete(favouriteEntity)
                        }
                    }
                    runBlocking {
                        job.join()
                    }
                    getData(searchWord)
                }

                override fun onAddClick(shoppingListEntity: ShoppingListEntity) {
                    val job = lifecycleScope.launch(Dispatchers.IO) {

                        val isAdded = viewModelShopping.checkExist(shoppingListEntity.mealName)
                        if (!isAdded) {
                            //insert fav
                            viewModelShopping.insert(shoppingListEntity)
                        } else {
                            //remove fav
                            viewModelShopping.delete(shoppingListEntity.mealName)
                        }
                    }
                    runBlocking {
                        job.join()
                    }
                    getData(searchWord)
                }
            })
        binding.searchRv.layoutManager =
            LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
        binding.searchRv.adapter = mealsAdapter
    }


    private fun initListeners() {
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mealsAdapter.notifyDataSetChanged()
                getData(binding.searchEt.text.toString())
                searchWord = binding.searchEt.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private suspend fun checkExist(idMeal: String): Boolean {
        return viewModel.checkExist(idMeal)
    }

    private suspend fun checkAdded(mealName: String): Boolean {
        return viewModelShopping.checkExist(mealName)
    }
}