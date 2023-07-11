package com.example.recipeapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.AlphabetsAdapter
import com.example.recipeapp.adapter.MealsAdapter
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.models.MealsModel
import com.example.recipeapp.utils.Helper
import com.example.recipeapp.utils.InternetManager
import com.example.recipeapp.utils.Url
import com.example.recipeapp.utils.VolleyRequests
import org.json.JSONObject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var isPlanMealSetTrue: Boolean = false
    private lateinit var alphabetAdapter: AlphabetsAdapter
    private lateinit var alphabets: ArrayList<String>
    private var selectedChar: String = "A"
    private lateinit var volleyRequests: VolleyRequests
    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var mList: ArrayList<MealsModel>
    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        initTasks()
        setAlphabets()
        initListeners()
        getRecipes()
    }

    private fun getRecipes() {
        if (InternetManager().checkInternet(requireContext())) {
            binding.progressBar.visibility = View.VISIBLE
            binding.dishRv.visibility = View.GONE
            mList = ArrayList()
            volleyRequests.makeGetRequest(requireContext(), Url.MEAL_URL + "?f=$selectedChar")
            volleyRequests.setVolleyRequest(object : VolleyRequests.VolleyRequestsListener {
                override fun onDataLoaded(jsonObject: JSONObject) {

                    if (jsonObject.has("meals") && !jsonObject.get("meals").equals(null)) {
                        if (jsonObject.getJSONArray("meals").length() > 0) {
                            for (i in 0 until jsonObject.getJSONArray("meals").length()) {
                                val jsonObj = jsonObject.getJSONArray("meals").getJSONObject(i)

                                val idMeal = jsonObj.getString("idMeal")
                                val strMeal = jsonObj.getString("strMeal")
                                val strArea = jsonObj.getString("strArea")
                                val strCategory = jsonObj.getString("strCategory")
                                val strMealThumb = jsonObj.getString("strMealThumb")

                                // Access the instructions separately since it is a multiline string
                                val strInstructions = jsonObj.getString("strInstructions")

                                // Create an Ingredients list
                                val ingredients = mutableListOf<Pair<String, String>>()
                                for (j in 1..20) {
                                    val ingredient = jsonObj.getString("strIngredient$j")
                                    val measure = jsonObj.getString("strMeasure$j")
                                    if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                                        ingredients.add(Pair(ingredient, measure))
                                    }
                                }

                                // Create the MealsModel object
                                val mealsModel = MealsModel(
                                    null,
                                    idMeal,
                                    strArea,
                                    strCategory,
                                    null,
                                    null,
                                    null,
                                    strInstructions,
                                    strMeal,
                                    strMealThumb,
                                    null,
                                    null,
                                    null,
                                    ingredients
                                )
                                mList.add(mealsModel)
                            }
                        }
                        binding.progressBar.visibility = View.GONE
                    } else {
                        binding.dishRv.visibility = View.GONE
                        binding.noResultFoundTv.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                    if (mList.size > 0) {
                        binding.dishRv.visibility = View.VISIBLE
                        binding.noResultFoundTv.visibility = View.GONE
                        setData()
                    }
                }

                override fun onError(e: Exception) {
                    Helper().makeToast(requireContext(), e.toString())
                }
            })
        } else {
            InternetManager().showAlertDialog(requireContext())
        }
    }

    private fun setData() {
        mealsAdapter = MealsAdapter(mList, mContext, isPlanMealSetTrue)
        binding.dishRv.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        binding.dishRv.adapter = mealsAdapter
    }

    private fun initTasks() {
        //initializing alphabets
        Helper().generateAlphabets()
        mContext = requireContext()

        alphabets = Helper.alphabets
        volleyRequests = VolleyRequests()
    }

    private fun setAlphabets() {
        alphabetAdapter =
            AlphabetsAdapter(requireContext(), alphabets, "A", object : AlphabetsAdapter.OnClick {
                @SuppressLint("NotifyDataSetChanged")
                override fun onAlphabetClicked(alphabet: String) {
                    //for pagination selected character
                    selectedChar = alphabet
                    alphabetAdapter.notifyDataSetChanged()
                    getRecipes()
                }
            })
        binding.alphabetRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.alphabetRv.adapter = alphabetAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListeners() {
        binding.planMealTv.setOnClickListener {
            if (isPlanMealSetTrue) {
                binding.planMealTv.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.meal_selector_off)
                isPlanMealSetTrue = false
            } else {
                binding.planMealTv.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.meal_selector_on)
                isPlanMealSetTrue = true
            }
            setData()
        }
    }
}