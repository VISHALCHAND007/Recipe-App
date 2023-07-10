package com.example.recipeapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.AlphabetsAdapter
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.utils.Helper
import com.example.recipeapp.utils.Url
import com.example.recipeapp.utils.VolleyRequests
import org.json.JSONObject

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var isPlanMealOn: Boolean = false
    private lateinit var adapter: AlphabetsAdapter
    private lateinit var alphabets: ArrayList<String>
    private var selectedChar: String = "A"
    private lateinit var volleyRequests: VolleyRequests

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
        volleyRequests.makeGetRequest(requireContext(), Url.MEAL_URL+"?f=$selectedChar")
        volleyRequests.setVolleyRequest(object: VolleyRequests.VolleyRequestsListener{
            override fun onDataLoaded(jsonObject: JSONObject) {

            }

            override fun onError(e: Exception) {
//                Helper().makeToast(requireContext(), e.toString())
            }

        })
    }

    private fun initTasks() {
        //initializing alphabets
        Helper().generateAlphabets()
        volleyRequests = VolleyRequests()

        alphabets = Helper.alphabets
    }

    private fun setAlphabets() {
        adapter = AlphabetsAdapter(requireContext(), alphabets, "A", object: AlphabetsAdapter.OnClick{
            @SuppressLint("NotifyDataSetChanged")
            override fun onAlphabetClicked(alphabet: String) {
                //for pagination selected character
                selectedChar = alphabet
                adapter.notifyDataSetChanged()
            }
        })
        binding.alphabetRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.alphabetRv.adapter = adapter
    }
    private fun initListeners() {
        binding.planMealTv.setOnClickListener {
            if(isPlanMealOn) {
                binding.planMealTv.background = ContextCompat.getDrawable(requireContext(), R.drawable.meal_selector_off)
                isPlanMealOn = false
            } else {
                binding.planMealTv.background = ContextCompat.getDrawable(requireContext(), R.drawable.meal_selector_on)
                isPlanMealOn = true
            }
        }
    }
}