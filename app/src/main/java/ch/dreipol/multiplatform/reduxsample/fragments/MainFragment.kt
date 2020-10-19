package ch.dreipol.multiplatform.reduxsample.fragments

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
import ch.dreipol.multiplatform.reduxsample.R
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentMainBinding
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.MainScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import java.lang.IllegalArgumentException
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
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_dashboard -> rootDispatch(NavigationAction.DASHBOARD)
                R.id.action_info -> rootDispatch(NavigationAction.INFO)
                R.id.action_settings -> rootDispatch(NavigationAction.SETTINGS)
            }
            true
        }
        subscription = subscribeNavigationState()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscription.invoke()
    }

    override fun updateNavigationState(navigationState: NavigationState) {
        val resourceId = when (navigationState.screens.last()) {
            MainScreen.DASHBOARD -> R.id.dashboardFragment
            MainScreen.INFORMATION -> R.id.infoFragment
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
            R.id.dashboardFragment -> R.id.action_dashboard
            R.id.infoFragment -> R.id.action_info
            R.id.settingsFragment -> R.id.action_settings
            else -> throw IllegalArgumentException()
        }
        if (binding.bottomNavigationView.selectedItemId != itemId) {
            binding.bottomNavigationView.selectedItemId = itemId
        }
    }
}