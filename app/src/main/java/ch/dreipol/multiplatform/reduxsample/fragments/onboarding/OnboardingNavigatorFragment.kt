package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Navigator
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.subscribeNavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingNavigatorBinding
import ch.dreipol.multiplatform.reduxsample.fragments.BaseFragment
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingView
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import org.reduxkotlin.StoreSubscriber

class OnboardingNavigatorFragment :
    BaseFragment<FragmentOnboardingNavigatorBinding, OnboardingView>(),
    Navigator<AppState>,
    OnboardingView {
    override val store = getAppConfiguration().reduxSampleApp.store
    override val presenterObserver = PresenterLifecycleObserver(this)

    private lateinit var adapter: OnboardingAdapter
    private lateinit var subscription: StoreSubscriber
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var onPageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun createBinding(): FragmentOnboardingNavigatorBinding {
        return FragmentOnboardingNavigatorBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        viewBinding.closeButton.setOnClickListener { rootDispatch(NavigationAction.ONBOARDING_END) }
        viewBinding.backIcon.setOnClickListener { requireActivity().onBackPressed() }
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, false) { }
        setupViewPager()
        subscription = subscribeNavigationState()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscription.invoke()
        viewBinding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    override fun getNavigationState(): NavigationState {
        return store.state.navigationState
    }

    override fun updateNavigationState(navigationState: NavigationState) {
        val onboardingScreen = navigationState.currentScreen as? OnboardingScreen ?: return
        if (onboardingScreen.canGoBack) {
            onBackPressedCallback.isEnabled = true
            viewBinding.backIcon.visibility = View.VISIBLE
        } else {
            onBackPressedCallback.isEnabled = false
            viewBinding.backIcon.visibility = View.GONE
        }
        val viewPagerIndex = getViewPagerIndex(onboardingScreen)
        if (viewBinding.viewPager.currentItem == viewPagerIndex) {
            return
        }
        viewBinding.viewPager.setCurrentItem(viewPagerIndex, true)
    }

    override fun render(onboardingViewState: OnboardingViewState) {
        viewBinding.closeButton.visibility = if (onboardingViewState.closeEnabled) View.VISIBLE else View.INVISIBLE
        adapter.stepsCount = onboardingViewState.onboardingViewCount
        viewBinding.dotsIndicator.visibility = if (onboardingViewState.dotIndicatorsVisible) View.VISIBLE else View.GONE
        adapter.notifyDataSetChanged()
    }

    private fun setupViewPager() {
        adapter = OnboardingAdapter(this)
        viewBinding.viewPager.adapter = adapter
        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val onboardingScreen = store.state.navigationState.currentScreen as? OnboardingScreen ?: return
                val viewPagerIndex = getViewPagerIndex(onboardingScreen)
                if (position == viewPagerIndex) {
                    return
                }
                if (position > viewPagerIndex) {
                    rootDispatch(NavigationAction.ONBOARDING_NEXT)
                } else {
                    rootDispatch(NavigationAction.BACK)
                }
            }
        }
        viewBinding.viewPager.registerOnPageChangeCallback(onPageChangeCallback)
        viewBinding.dotsIndicator.setViewPager2(viewBinding.viewPager)
    }

    private fun getViewPagerIndex(onboardingScreen: OnboardingScreen): Int {
        return onboardingScreen.step - 1
    }
}

class OnboardingAdapter(parent: Fragment, var stepsCount: Int = 1) : FragmentStateAdapter(parent) {
    override fun getItemCount(): Int {
        return stepsCount
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