package com.example.recipeapp.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.adapter.FavouriteAdapter
import com.example.recipeapp.databinding.FragmentFavouriteBinding
import com.example.recipeapp.room.FavouriteDao
import com.example.recipeapp.room.FavouriteDatabase
import com.example.recipeapp.room.FavouriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var adapter: FavouriteAdapter
    private lateinit var dao: FavouriteDao
    private lateinit var mContext: Context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        getData()
        initListeners()
    }

    private fun getData() {
        mContext = requireContext()
        binding.favouriteRv.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            dao = FavouriteDatabase.getDatabaseInstance(mContext).favouriteDao()
        }
        Handler(Looper.myLooper()!!).postDelayed({
            dao.getAll().observe(viewLifecycleOwner) {
                checkDataExists(it)
                adapter = FavouriteAdapter(requireContext(), it.reversed(), object : FavouriteAdapter.OnClick {
                    override fun onFavouriteImgClick(favouriteEntity: FavouriteEntity) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            dao.delete(favouriteEntity)
                        }
                    }
                })
                binding.favouriteRv.adapter = adapter
            }
        }, 1000)
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

    private fun initListeners() {

    }
}