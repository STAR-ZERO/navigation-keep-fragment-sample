package com.star_zero.navigation_keep_fragment_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.star_zero.navigation_keep_fragment_sample.databinding.ActivityMainBinding
import com.star_zero.navigation_keep_fragment_sample.navigation.KeepStateNavigator
import com.star_zero.navigation_keep_fragment_sample.util.NavigationViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navigationViewModel: NavigationViewModel by lazy {
        ViewModelProviders.of(this).get(NavigationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)

        // get fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!

        // setup custom navigator
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)
        navController.navigatorProvider += navigator

        // set navigation graph
        navController.setGraph(R.navigation.nav_graph)

        binding.bottomNav.setupWithNavController(navController)

        navigationViewModel.navigateDetail.observe(this, Observer {
            binding.bottomNav.selectedItemId = R.id.navigation_home
        })
    }
}
