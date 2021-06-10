/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.fragments.settings

import android.os.Bundle
import android.view.View
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.ZipSettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.ZipSettingsViewState
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentZipSettingsBinding
import ch.dreipol.rezhycle.fragments.BaseFragment
import ch.dreipol.rezhycle.fragments.KeyboardUsingFragment
import ch.dreipol.rezhycle.fragments.SystemBarColor

class ZipSettingsFragment : BaseFragment<FragmentZipSettingsBinding, ZipSettingsView>(), ZipSettingsView, KeyboardUsingFragment {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentZipSettingsBinding {
        return FragmentZipSettingsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.enterZipView.setLabelColor(R.color.primary_dark)
        viewBinding.enterZipView.focus(requireActivity())
    }

    override fun overrideSystemBarColor(): SystemBarColor {
        return SystemBarColor.LIGHT
    }

    override fun render(zipSettingsViewState: ZipSettingsViewState) {
        bindHeader(zipSettingsViewState.headerViewState, viewBinding.header)
        viewBinding.enterZipView.update(zipSettingsViewState.enterZipViewState)
    }
}