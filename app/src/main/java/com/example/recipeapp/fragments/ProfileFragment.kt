package com.example.recipeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.databinding.FragmentProfileBinding
import com.example.recipeapp.utils.Helper

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var mListRestrictions: ArrayList<String>
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
        checkEmpty()
    }

    private fun checkEmpty() {
        if(mListRestrictions.isEmpty()) {
            binding.noDataTv.visibility = View.VISIBLE
        }
    }

    private fun initListeners() {
        binding.profileImage.setOnClickListener {
            Helper().makeToast(requireContext(), "Feature not added.")
        }

        binding.addBtn.setOnClickListener {
            if(binding.inputEt.text.isNotEmpty()) {

            }
        }

        binding.saveBtn.setOnClickListener {

        }
    }

    private fun updateList() {

    }
}