package com.example.recipeapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.adapter.FavouriteAdapter
import com.example.recipeapp.databinding.FragmentFavouriteBinding
import com.example.recipeapp.room.entity.FavouriteEntity
import com.example.recipeapp.viewModel.FavouriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var adapter: FavouriteAdapter
    private lateinit var mContext: Context
    private lateinit var viewModel: FavouriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        getData()
        return binding.root
    }

    private fun getData() {
        mContext = requireContext()
        viewModel = ViewModelProvider(requireActivity())[FavouriteViewModel::class.java]

        binding.favouriteRv.layoutManager = LinearLayoutManager(requireContext())
        viewModel.allData.observe(viewLifecycleOwner) {
            checkDataExists(it)
            adapter = FavouriteAdapter(
                requireContext(),
                it.reversed(),
                object : FavouriteAdapter.OnClick {
                    override fun onFavouriteImgClick(favouriteEntity: FavouriteEntity) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            viewModel.delete(favouriteEntity)
                        }
                    }
                })
            binding.favouriteRv.adapter = adapter
        }
    }

    private fun checkDataExists(list: List<FavouriteEntity>) {
        binding.progressRl.visibility = View.GONE

        if (list.isNotEmpty()) {
            binding.noResultFoundTv.visibility = View.GONE
            binding.favouriteRv.visibility = View.VISIBLE

//            setData()
        } else {
            binding.noResultFoundTv.visibility = View.VISIBLE
            binding.favouriteRv.visibility = View.GONE
        }
    }
}