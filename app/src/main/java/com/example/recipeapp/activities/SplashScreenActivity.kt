package com.example.recipeapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        startAnimation()
        Handler(mainLooper).postDelayed({
            val homeIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(homeIntent)
        }, 3000)
    }

    private fun startAnimation() {
        binding.appNameTv.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.custom_animation
            )
        )
        binding.appNameTv.animate().start()
    }


}