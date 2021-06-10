/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Navigator
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Screen
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.subscribeNavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.dreimultiplatform.reduxkotlin.selectFixed
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.MainScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.OpenedWithReminderNotification
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import ch.dreipol.rezhycle.utils.*
import com.mikepenz.aboutlibraries.LibsBuilder
import kotlin.time.ExperimentalTime
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber

class MainActivity : ReduxSampleActivity(), Navigator<AppState> {

    private lateinit var cancelNavigationSubscription: StoreSubscriber
    private lateinit var cancelSettingsSubscription: StoreSubscriber

    override val store: Store<AppState>
        get() {
            return getAppConfiguration().reduxSampleApp.store
        }

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAppConfiguration().platformFeatures.init(this)

        cancelNavigationSubscription = subscribeNavigationState()
        cancelSettingsSubscription = store.selectFixed({ it.settingsState }) {
            store.state.settingsState.state?.let {
                updateReminders(this, it.nextReminders)
            }
        }
        if (intent.getStringExtra(STARTED_FROM_EXTRA) == REMINDER_NOTIFICATION) {
            rootDispatch(OpenedWithReminderNotification())
        }
    }

    override fun attachBaseContext(base: Context?) {
        base?.let {
            val appLanguage = store.state.appLanguage
            val resourceContext = updateResources(it, appLanguage)
            super.attachBaseContext(resourceContext)
        } ?: run {
            super.attachBaseContext(base)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        rootDispatch(NavigationAction.BACK)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelNavigationSubscription()
        cancelSettingsSubscription()
    }

    override fun updateNavigationState(navigationState: NavigationState) {
        if (navigationState.screens.isEmpty()) {
            return
        }
        val navController = findNavController(R.id.main_nav_host_fragment)
        val backStack = navController.getBackStackList()
        val expectedScreen = navigationState.screens.last()
        val expectedDestinationId = screenToResourceId(expectedScreen)
        if (navController.currentDestination?.id != expectedDestinationId) {
            navController.navigate(
                expectedDestinationId, createBundle(expectedScreen),
                buildNavOptions(expectedDestinationId, navigationState, backStack)
            )
        }
    }

    override fun getNavigationState(): NavigationState {
        return store.state.navigationState
    }

    private fun createBundle(screen: Screen): Bundle {
        val bundle = Bundle()
        when (screen) {
            MainScreen.LICENCES -> {
                bundle.putSerializable(
                    "data",
                    LibsBuilder()
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withLicenseShown(true)
                )
            }
        }
        return bundle
    }

    private fun screenToResourceId(screen: Screen): Int {
        if (screen is OnboardingScreen) {
            return R.id.onboardingNavigatorFragment
        }
        return when (screen) {
            MainScreen.CALENDAR, MainScreen.COLLECTION_POINT_MAP, MainScreen.SETTINGS -> R.id.mainFragment
            MainScreen.CALENDAR_SETTINGS -> R.id.disposalTypesFragment
            MainScreen.ZIP_SETTINGS -> R.id.zipSettingsFragment
            MainScreen.NOTIFICATION_SETTINGS -> R.id.notificationSettingsFragment
            MainScreen.LANGUAGE_SETTINGS -> R.id.languageSettingsFragment
            MainScreen.IMPRINT -> R.id.imprintFragment
            MainScreen.LICENCES -> R.id.licenceFragment
            else -> throw IllegalArgumentException()
        }
    }

    private fun buildNavOptions(
        @IdRes destinationId: Int,
        navigationState: NavigationState,
        backStack: List<NavBackStackEntry>
    ): NavOptions {
        val builder = NavOptions.Builder()
        if (backStack.size >= navigationState.screens.size) {
            val popTo = findFirstMatchingBackStackScreen(navigationState.screens, backStack)
            popTo?.let { builder.setPopUpTo(screenToResourceId(it), false) }
                ?: builder.setPopUpTo(backStack.first().destination.id, true)
        }
        if (destinationId == R.id.mainFragment) {
            builder.setLaunchSingleTop(true)
        }
        return builder.build()
    }

    private fun findFirstMatchingBackStackScreen(screens: List<Screen>, backStack: List<NavBackStackEntry>): Screen? {
        return screens.firstOrNull { screen -> backStack.find { it.destination.id == screenToResourceId(screen) } != null }
    }
}

@SuppressLint("RestrictedApi")
fun NavController.getBackStackList(): List<NavBackStackEntry> {
    val backStack = backStack.toMutableList()
    backStack.remove(backStack.first())
    return backStack
}