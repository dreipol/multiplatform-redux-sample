/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Navigator
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.subscribeNavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.MainScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentMainBinding
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber

class MainFragment : Fragment(), Navigator<AppState> {

    override val store: Store<AppState>
        get() {
            return getAppConfiguration().reduxSampleApp.store
        }

    private lateinit var binding: FragmentMainBinding
    private lateinit var subscription: StoreSubscriber
    private val navController: NavController by lazy {
        val navHost = childFragmentManager.findFragmentById(R.id.tab_nav_host_fragment) as NavHostFragment
        navHost.navController
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        updateSystemBarColor(SystemBarColor.LIGHT)
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_calendar -> rootDispatch(NavigationAction.CALENDAR)
                R.id.action_map -> rootDispatch(NavigationAction.COLLECTION_POINT_MAP)
                R.id.action_settings -> rootDispatch(NavigationAction.SETTINGS)
            }
            true
        }
        subscription = subscribeNavigationState()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscription()
    }

    override fun updateNavigationState(navigationState: NavigationState) {
        val resourceId = when (navigationState.screens.last()) {
            MainScreen.CALENDAR -> R.id.calendarFragment
            MainScreen.COLLECTION_POINT_MAP -> R.id.collectionPointMapFragment
            MainScreen.SETTINGS -> R.id.settingsFragment
            else -> return
        }

        if (navController.currentDestination?.id != resourceId) {
            navigateTo(resourceId)
        }
        syncBottomNavigation(resourceId)
    }

    override fun getNavigationState(): NavigationState {
        return store.state.navigationState
    }

    private fun navigateTo(@IdRes destinationId: Int) {
        navController.navigate(
            destinationId, Bundle.EMPTY,
            NavOptions.Builder().setPopUpTo(destinationId, true).build()
        )
    }

    private fun syncBottomNavigation(@IdRes destinationId: Int) {
        val itemId = when (destinationId) {
            R.id.calendarFragment -> R.id.action_calendar
            R.id.collectionPointMapFragment -> R.id.action_map
            R.id.settingsFragment -> R.id.action_settings
            else -> throw IllegalArgumentException()
        }
        if (binding.bottomNavigationView.selectedItemId != itemId) {
            binding.bottomNavigationView.selectedItemId = itemId
        }
    }
}