    package com.example.recipeapp.activities

    import android.content.DialogInterface
    import android.os.Bundle
    import android.view.MenuItem
    import androidx.appcompat.app.ActionBarDrawerToggle
    import androidx.appcompat.app.AlertDialog
    import androidx.appcompat.app.AppCompatActivity
    import androidx.appcompat.app.AppCompatDelegate
    import androidx.core.view.GravityCompat
    import androidx.fragment.app.Fragment
    import com.example.recipeapp.R
    import com.example.recipeapp.databinding.ActivityMainBinding
    import com.example.recipeapp.fragments.FavouriteFragment
    import com.example.recipeapp.fragments.HomeFragment
    import com.example.recipeapp.fragments.MealPlanFragment
    import com.example.recipeapp.fragments.ProfileFragment
    import com.example.recipeapp.fragments.ShoppingListFragment
    import com.example.recipeapp.utils.Helper

    class MainActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMainBinding
        private var previousMenuItem: MenuItem? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            init()
        }

        private fun init() {
            initTasks()
            setUpToolBar()
            setUpHomeFragment()
            initListeners()
            setUpHamburgerFunctionality()
        }

        private fun setUpHomeFragment() {
            if(binding.navigationView.checkedItem?.itemId!= R.id.home) {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameLayout, HomeFragment()).commit()
                supportActionBar?.title = "Home"
                binding.navigationView.setCheckedItem(R.id.home)
            }
        }

        private fun setUpHamburgerFunctionality() {
            val actionDrawerToggle = ActionBarDrawerToggle(
                this@MainActivity,
                binding.drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
            )
            binding.drawerLayout.addDrawerListener(actionDrawerToggle)
            actionDrawerToggle.syncState()
        }

        private fun setUpToolBar() {
            setSupportActionBar(binding.toolBar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        private fun initTasks() {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        private fun initListeners() {
            binding.navigationView.setNavigationItemSelectedListener {
                //setting the selected option checked
                if (previousMenuItem != null) {
                    previousMenuItem?.isChecked = false
                }
                it.isCheckable = true
                it.isChecked = true
                previousMenuItem = it
                when (it.itemId) {
                    R.id.home -> {
                        setUpHomeFragment()
                    }

                    R.id.favourite -> {
                        setUpPage("Favourite", FavouriteFragment())
                    }

                    R.id.shoppingList -> {
                        setUpPage("Shopping List", ShoppingListFragment())
                    }

                    R.id.mealPlan -> {
                        setUpPage("Meal Plan", MealPlanFragment())
                    }

                    R.id.profile -> {
                        setUpPage("Profile", ProfileFragment())
                    }
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                return@setNavigationItemSelectedListener true
            }
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
            return super.onOptionsItemSelected(item)
        }

        private fun setUpPage(title: String, fragment: Fragment) {
            binding.toolBar.title = title
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit()
        }

        @Deprecated("Deprecated in Java")
        override fun onBackPressed() {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            when (supportFragmentManager.findFragmentById(R.id.frameLayout)) {
                !is HomeFragment -> {
                    setUpHomeFragment()
                }

                else -> {
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setMessage("Do you want to exit?")
                    alertDialog.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                        finishAffinity()
                    }
                    alertDialog.setNegativeButton("No") {_: DialogInterface, _: Int ->
                    }
                    alertDialog.show()
                }
            }
        }
    }