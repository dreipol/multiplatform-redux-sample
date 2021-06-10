/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.fragments.onboarding

import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingSubView
import ch.dreipol.rezhycle.databinding.FragmentOnboardingBinding
import ch.dreipol.rezhycle.fragments.BaseFragment
import ch.dreipol.rezhycle.utils.getString

abstract class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingSubView>(), OnboardingSubView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentOnboardingBinding {
        return FragmentOnboardingBinding.inflate(layoutInflater)
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        viewBinding.title.text = onboardingSubState.title?.let { requireContext().getString(it) }
        viewBinding.primary.text = requireContext().getString(onboardingSubState.primary)
        viewBinding.primary.isEnabled = onboardingSubState.primaryEnabled
        viewBinding.primary.setOnClickListener { rootDispatch(onboardingSubState.primaryAction) }
    }
}