package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Navigator
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.subscribeNavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingNavigatorBinding
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingView
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import org.reduxkotlin.StoreSubscriber

class OnboardingNavigatorFragment : Fragment(), Navigator<AppState>, OnboardingView {
    override val store = getAppConfiguration().reduxSampleApp.store

    private lateinit var binding: FragmentOnboardingNavigatorBinding
    private lateinit var subscription: StoreSubscriber
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOnboardingNavigatorBinding.inflate(layoutInflater)
        binding.viewPager.adapter = OnboardingAdapter(this)
        binding.closeButton.setOnClickListener { rootDispatch(NavigationAction.ONBOARDING_END) }
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, false) {
            rootDispatch(NavigationAction.BACK)
        }
        subscription = subscribeNavigationState()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscription.invoke()
    }

    override fun getNavigationState(): NavigationState {
        return store.state.navigationState
    }

    override fun updateNavigationState(navigationState: NavigationState) {
        val onboardingScreen = navigationState.currentScreen as? OnboardingScreen ?: return
        when (onboardingScreen.step) {
            1 -> onBackPressedCallback.isEnabled = false
            else -> onBackPressedCallback.isEnabled = true
        }
        val viewPagerIndex = onboardingScreen.step - 1
        if (binding.viewPager.currentItem == viewPagerIndex) {
            return
        }
        binding.viewPager.setCurrentItem(viewPagerIndex, true)
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        binding.closeButton.isEnabled = onboardingSubState.closeEnabled
    }
}

class OnboardingAdapter(parent: Fragment) : FragmentStateAdapter(parent) {
    override fun getItemCount(): Int {
        return OnboardingViewState.ONBOARDING_VIEW_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EnterZipFragment()
            1 -> SelectDisposalTypesFragment()
            2 -> AddNotificationFragment()
            3 -> FinishFragment()
            else -> throw IllegalArgumentException()
        }
    }
}