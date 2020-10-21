package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingAddNotificationBinding
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateAddNotification
import ch.dreipol.multiplatform.reduxsample.shared.ui.AddNotificationState
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState

class AddNotificationFragment : OnboardingFragment() {

    private lateinit var addNotificationBinding: FragmentOnboardingAddNotificationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        addNotificationBinding = viewBinding.fragmentOnboardingAddNotification
        addNotificationBinding.root.visibility = View.VISIBLE
        return view
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        if (onboardingSubState !is AddNotificationState) return
        super.render(onboardingSubState)
        addNotificationBinding.toggle.setOnCheckedChangeListener { _, _ -> }
        addNotificationBinding.toggle.isChecked = onboardingSubState.addNotification
        addNotificationBinding.toggle.setOnCheckedChangeListener { _, isChecked -> rootDispatch(UpdateAddNotification(isChecked)) }
    }
}