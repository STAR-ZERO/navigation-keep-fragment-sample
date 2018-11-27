package com.star_zero.navigation_keep_fragment_sample.navigation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("keep_state_fragment") // `keep_state_fragment` is used in navigation xml
class KeepStateNavigator(
    context: Context,
    private val manager: FragmentManager, // Should pass childFragmentManager.
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {

        val tag = destination.id.toString()
        val transaction = manager.beginTransaction()

        val currentFragment = manager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }

        var fragment = manager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = destination.createFragment(args)
            transaction.add(containerId, fragment, tag)
        } else {
            transaction.attach(fragment)
        }

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commit()

        dispatchOnNavigatorNavigated(destination.id, BACK_STACK_DESTINATION_ADDED)
    }
}
