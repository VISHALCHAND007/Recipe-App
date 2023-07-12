package com.example.recipeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.adapter.RestrictionsAdapter
import com.example.recipeapp.databinding.FragmentProfileBinding
import com.example.recipeapp.room.entity.ProfileEntity
import com.example.recipeapp.utils.Helper
import com.example.recipeapp.viewModel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var mListRestrictions: ArrayList<String>
    private lateinit var viewModel: ProfileViewModel
    private var doUpdate: Boolean = false
    private lateinit var cuisineType: String
    private var setData:Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        initTasks()
        initListeners()
    }

    private fun initTasks() {
        mListRestrictions = ArrayList()
        binding.dietaryRestrictionsRv.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]

        val job = lifecycleScope.launch(Dispatchers.IO) {
            val item = viewModel.getData()
            if (item.isNotEmpty()) {
                setData = true
                mListRestrictions = item[0].dietaryRestrictions
                cuisineType = item[0].cuisineType
            }
        }
        runBlocking {
            job.join()
        }
        checkEmpty()
    }

    private fun checkEmpty() {
        if (mListRestrictions.isEmpty()) {
            binding.noDataTv.visibility = View.VISIBLE
        } else {
            if(setData) {
                binding.noDataTv.visibility = View.GONE
                for (i in 0..8) {
                    if (binding.cuisineType.getItemAtPosition(i) == cuisineType) {
                        binding.cuisineType.setSelection(i)
                        break
                    }
                }
            }
            setData()
        }
    }

    private fun initListeners() {
        binding.profileImage.setOnClickListener {
            Helper().makeToast(requireContext(), "Feature not added.")
        }

        binding.addBtn.setOnClickListener {
            if (binding.inputEt.text.isNotEmpty()) {
                mListRestrictions.add(binding.inputEt.text.toString())
                binding.inputEt.setText("")
                checkEmpty()
                setData()
            }
        }

        binding.saveBtn.setOnClickListener {
            if (binding.cuisineType.selectedItem.equals("Select Cuisine")) {
                Helper().makeToast(requireContext(), "Please select your preferred cuisine.")
            } else if (mListRestrictions.isEmpty()) {
                Helper().makeToast(requireContext(), "No dietary restrictions are added.")
            } else {
                val profileData =
                    ProfileEntity(binding.cuisineType.selectedItem.toString(), mListRestrictions)

                val job = lifecycleScope.launch(Dispatchers.IO) {
                    val list = viewModel.getData()
                    doUpdate = list.isNotEmpty()
                }
                runBlocking {
                    job.join()
                }


                if (doUpdate) {
                    viewModel.delete()
                    viewModel.insert(profileData)
                } else {
                    viewModel.insert(profileData)
                }
                Helper().makeToast(requireContext(), "Saved")
            }
        }
    }

    private fun setData() {
        binding.dietaryRestrictionsRv.adapter =
            RestrictionsAdapter(requireContext(), mListRestrictions)
    }
}