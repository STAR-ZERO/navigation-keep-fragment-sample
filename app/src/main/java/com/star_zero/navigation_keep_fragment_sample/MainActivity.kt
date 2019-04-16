package com.star_zero.navigation_keep_fragment_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.star_zero.navigation_keep_fragment_sample.databinding.ActivityMainBinding
import com.star_zero.navigation_keep_fragment_sample.navigation.KeepStateBackStackNavigator
import com.star_zero.navigation_keep_fragment_sample.navigation.KeepStateNavigator
import com.star_zero.navigation_keep_fragment_sample.navigation.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        this.navController = findNavController(R.id.nav_host_fragment)

        // get fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!

        // setup custom navigator
        //val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)
        //navController.navigatorProvider += navigator
        // set navigation graph
        //navController.setGraph(R.navigation.nav_graph)
        // binding.bottomNav.setupWithNavController(navController)

        // With BackStack
        val navigator = KeepStateBackStackNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)
        this.navController.navigatorProvider += navigator
        // set navigation graph
        this.navController.setGraph(R.navigation.nav_graph_back_stack)
        binding.bottomNav.setupWithNavController(this.navController, navigator)
    }

    override fun onBackPressed() {
        this.navController.popBackStack(this.navController.currentDestination!!.id, true)
        super.onBackPressed()
    }
}
