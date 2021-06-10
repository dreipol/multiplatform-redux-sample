/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.multiplatform.reduxsample.shared.ui.AddNotificationState
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentOnboardingAddNotificationBinding
import ch.dreipol.rezhycle.utils.getString
import ch.dreipol.rezhycle.view.NotificationListAdapter

class AddNotificationFragment : OnboardingFragment() {

    private lateinit var addNotificationBinding: FragmentOnboardingAddNotificationBinding
    private lateinit var notificationListAdapter: NotificationListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        addNotificationBinding = viewBinding.fragmentOnboardingAddNotification
        addNotificationBinding.root.visibility = View.VISIBLE
        notificationListAdapter = NotificationListAdapter(
            requireContext(), listOf(), false, extraBottomSpaceLastItem = R.dimen.onboarding_button_container_height
        )
        addNotificationBinding.notificationList.adapter = notificationListAdapter
        return view
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        if (onboardingSubState !is AddNotificationState) return
        super.render(onboardingSubState)
        notificationListAdapter.notificationEnabled = onboardingSubState.addNotification
        notificationListAdapter.remindTimes = onboardingSubState.remindTimes
        notificationListAdapter.notificationToggleCD = requireContext().getString(onboardingSubState.notificationToggleCDKey)
        notificationListAdapter.checkIconCD = requireContext().getString(onboardingSubState.checkIconCDKey)
        notificationListAdapter.buildGroupedData()
        notificationListAdapter.notifyDataSetChanged()
    }
}