package com.god_brain.example

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.god_brain.example.databinding.Activity0MainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: Activity0MainBinding

    @SuppressLint("MutableCollectionMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity0MainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
    }

    fun navigate(action: NavDirections) {
        val navController = findNavController(this, binding.navHostFragment.id)
        navController.navigate(action)
    }

    fun navigateWithClearStack(action: NavDirections) {
        val navController = findNavController(this, binding.navHostFragment.id)

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_graph, inclusive = true)
            .build()

        navController.navigate(action, navOptions)
    }


}
