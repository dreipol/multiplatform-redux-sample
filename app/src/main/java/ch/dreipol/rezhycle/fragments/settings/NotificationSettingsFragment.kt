/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.addOrRemoveNotificationThunk
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.setRemindTimeThunk
import ch.dreipol.multiplatform.reduxsample.shared.ui.NotificationSettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.NotificationSettingsViewState
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentNotificationSettingsBinding
import ch.dreipol.rezhycle.fragments.BaseFragment
import ch.dreipol.rezhycle.fragments.SystemBarColor
import ch.dreipol.rezhycle.utils.getString
import ch.dreipol.rezhycle.view.NotificationListAdapter
import ch.dreipol.rezhycle.view.NotificationListTheme
import ch.dreipol.rezhycle.view.SelectDisposalTypesAdapter

class NotificationSettingsFragment :
    BaseFragment<FragmentNotificationSettingsBinding, NotificationSettingsView>(),
    NotificationSettingsView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    private lateinit var notificationAdapter: NotificationListAdapter
    private lateinit var disposalTypeAdapter: SelectDisposalTypesAdapter

    override fun createBinding(): FragmentNotificationSettingsBinding {
        return FragmentNotificationSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        notificationAdapter = NotificationListAdapter(
            requireContext(), emptyList(), true, NotificationListTheme.LIGHT,
            { remindTime ->
                rootDispatch(setRemindTimeThunk(remindTime))
            },
            {
                rootDispatch(addOrRemoveNotificationThunk())
            }
        )
        viewBinding.notification.adapter = notificationAdapter
        disposalTypeAdapter = SelectDisposalTypesAdapter(
            requireContext(), emptyMap(), R.color.primary_dark,
            { _, disposalType ->
                rootDispatch(addOrRemoveNotificationThunk(disposalType))
            }
        )
        viewBinding.disposalTypes.adapter = disposalTypeAdapter
        return view
    }

    override fun overrideSystemBarColor(): SystemBarColor {
        return SystemBarColor.LIGHT
    }

    override fun render(notificationSettingsViewState: NotificationSettingsViewState) {
        bindHeader(notificationSettingsViewState.headerViewState, viewBinding.header)
        viewBinding.introduction.text = requireContext().getString(notificationSettingsViewState.introductionKey)

        notificationAdapter.notificationEnabled = notificationSettingsViewState.notificationEnabled
        notificationAdapter.remindTimes = notificationSettingsViewState.remindTimes
        notificationAdapter.notificationToggleCD = requireContext().getString(notificationSettingsViewState.notificationToggleCDKey)
        notificationAdapter.checkIconCD = requireContext().getString(notificationSettingsViewState.checkIconCDKey)
        notificationAdapter.buildGroupedData()
        notificationAdapter.notifyDataSetChanged()

        disposalTypeAdapter.disposalTypes =
            DisposalType.values().map { if (notificationSettingsViewState.selectedDisposalTypes.contains(it)) it to true else it to false }
                .toMap()
        disposalTypeAdapter.toggleCDReplaceable = requireContext().getString(notificationSettingsViewState.disposalToggleCDReplaceableKey)
        disposalTypeAdapter.disposalImageCDReplaceable =
            requireContext().getString(notificationSettingsViewState.disposalImageCDReplaceableKey)
        disposalTypeAdapter.notifyDataSetChanged()

        val disposalTypeVisibility = if (notificationSettingsViewState.notificationEnabled) View.VISIBLE else View.GONE
        viewBinding.disposalTypes.visibility = disposalTypeVisibility
        viewBinding.introduction.visibility = disposalTypeVisibility
    }
}