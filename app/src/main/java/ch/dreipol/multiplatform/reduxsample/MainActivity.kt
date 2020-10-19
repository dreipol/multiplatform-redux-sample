package ch.dreipol.multiplatform.reduxsample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.*
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.MainScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import org.reduxkotlin.Store

class MainActivity : ReduxSampleActivity(), Navigator<AppState> {

    override val store: Store<AppState>
        get() {
            return getAppConfiguration().reduxSampleApp.store
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeNavigationState()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        rootDispatch(NavigationAction.BACK)
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
            when (navigationState.navigationDirection) {
                NavigationDirection.PUSH -> navController.navigate(
                    expectedDestinationId, Bundle.EMPTY,
                    buildNavOptions(expectedDestinationId, navigationState, backStack)
                )
                NavigationDirection.POP -> navController.popBackStack(expectedDestinationId, false)
            }
        }
    }

    override fun getNavigationState(): NavigationState {
        return store.state.navigationState
    }

    private fun screenToResourceId(screen: Screen): Int {
        if (screen is OnboardingScreen) {
            return when (screen.step) {
                1 -> R.id.onboardingFragment1
                2 -> R.id.onboardingFragment2
                3 -> R.id.onboardingFragment3
                4 -> R.id.onboardingFragment4
                else -> throw IllegalArgumentException()
            }
        }
        return when (screen) {
            MainScreen.DASHBOARD, MainScreen.INFORMATION, MainScreen.SETTINGS -> R.id.mainFragment
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