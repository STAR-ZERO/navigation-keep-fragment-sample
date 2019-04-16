package com.star_zero.navigation_keep_fragment_sample.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.core.view.forEach
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.MutableList
import kotlin.collections.count
import kotlin.collections.forEach
import kotlin.collections.last
import kotlin.collections.mutableListOf
import kotlin.collections.set
import kotlin.collections.toIntArray

// With BackStack
// TODO: This is just sample. I don't recommend using this in production.
@Navigator.Name("keep_state_fragment_back_stack") // `keep_state_fragment` is used in navigation xml
class KeepStateBackStackNavigator(
    private val context: Context,
    private val manager: FragmentManager, // Should pass childFragmentManager.
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    private var popBackStackListener: ((Int) -> Unit)? = null

    // this variable will be used to store backstacks list instead of super class mBackStack
    private val backStack = Stack<Int>()

    private val onBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        if (this.backStack.count() > this.manager.backStackEntryCount + 1) {
            this.backStack.pop()
            this.popBackStackListener?.invoke(backStack.peek())
        } else {
        }
    }

    override fun onBackPressAdded() {
        super.onBackPressAdded()
        this.manager.addOnBackStackChangedListener(onBackStackChangedListener)
    }

    override fun onBackPressRemoved() {
        super.onBackPressRemoved()
        this.manager.removeOnBackStackChangedListener(onBackStackChangedListener)
    }

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val transaction = this.manager.beginTransaction()

        val currentFragment = this.manager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }

        var fragment = this.manager.findFragmentByTag(tag)
        if (fragment == null) {
            val className = destination.className
            fragment = this.instantiateFragment(context, manager, className, args)
            transaction.add(this.containerId, fragment, tag)
        } else {
            transaction.attach(fragment)
        }

        //
        if (!this.backStack.isEmpty()) {
            transaction.addToBackStack(tag)
        }

        this.backStack.push(destination.id)

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commit()
        return destination
    }

    override fun popBackStack(): Boolean {
        if (this.backStack.isEmpty()) {
            return false
        }
        if (this.manager.isStateSaved) {
            return false
        }
        if (this.manager.backStackEntryCount > 0) {

        } // else, we're on the first Fragment, so there's nothing to pop from FragmentManager
        this.backStack.pop()
        return true
    }

    fun setPopBackStackListener(listener: (Int) -> Unit) {
        this.popBackStackListener = listener
    }

    // For rotation
    override fun onSaveState(): Bundle? {
        val bundle = super.onSaveState() ?: Bundle()
        bundle.putIntArray(KEY_BACK_STACK_IDS, this.backStack.toIntArray())
        return bundle
    }

    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)
        savedState?.getIntArray(KEY_BACK_STACK_IDS)?.forEach {
            this.backStack.push(it)
        }
    }

    companion object {
        private const val KEY_BACK_STACK_IDS = "back_stack_ids"
    }

    val lastVisitedDestinationId: Int
        get() = this.backStack.peek()
}

@SuppressLint("UseSparseArrays")
fun BottomNavigationView.setupWithNavController(navController: NavController, navigator: KeepStateBackStackNavigator) {
    val pagerBackStack = HashMap<Int, MutableList<Int>>()

    // initialize pager back stack
    this.menu.forEach {
        pagerBackStack[it.itemId] = mutableListOf(it.itemId)
    }

    setOnNavigationItemSelectedListener {
        // we must keep track fragment movement
        // but don't add destination id, if the last in list are same
        if ( pagerBackStack[this.selectedItemId]!!.last() != navigator.lastVisitedDestinationId) {
            pagerBackStack[this.selectedItemId]!!.add(navigator.lastVisitedDestinationId)
        }
        try {
            // navigate to stored hashmap backstack
            navController.navigate(pagerBackStack[it.itemId]!!.last())
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        // Change tab
        // find entry from stack pager lists
        var tabId : Int = 0
        pagerBackStack.forEach {
            if ( it.value.contains(destination.id) ) {
                tabId = it.key
            }
        }
        if (tabId == 0) {
            return@addOnDestinationChangedListener
        }
        (0 until menu.size()).forEach {
            val item = menu.getItem(it)
            if (item.itemId == tabId) {
                item.isChecked = true
            }
        }
    }

    navigator.setPopBackStackListener { id ->
        // Press back key
        // find entry from stack pager lists
        var tabId : Int = 0
        pagerBackStack.forEach {
            if ( it.value.contains(id) ) {
                tabId = it.key
            }
        }
        if (tabId == 0) {
            return@setPopBackStackListener
        }
        if (pagerBackStack[tabId]!!.size > 1) {
            pagerBackStack[tabId]!!.remove(pagerBackStack[tabId]!!.size - 1)
        }
        (0 until menu.size()).forEach {
            val item = menu.getItem(it)
            if (item.itemId == tabId) {
                item.isChecked = true
            }
        }
    }
}