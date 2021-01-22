package ch.dreipol.rezhycle.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.redux.addOrRemoveNotificationThunk
import ch.dreipol.multiplatform.reduxsample.shared.redux.setRemindTimeThunk
import ch.dreipol.multiplatform.reduxsample.shared.ui.NotificationSettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.NotificationSettingsViewState
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentNotificationSettingsBinding
import ch.dreipol.rezhycle.fragments.BaseFragment
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
            requireContext(), emptyList(), true, "", "", NotificationListTheme.WHITE,
            { remindTime ->
                rootDispatch(setRemindTimeThunk(remindTime))
            },
            {
                rootDispatch(addOrRemoveNotificationThunk())
            }
        )
        viewBinding.notification.adapter = notificationAdapter
        disposalTypeAdapter = SelectDisposalTypesAdapter(
            requireContext(), emptyMap(), "", R.color.test_app_blue,
            { _, disposalType ->
                rootDispatch(addOrRemoveNotificationThunk(disposalType))
            }
        )
        viewBinding.disposalTypes.adapter = disposalTypeAdapter
        return view
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
        disposalTypeAdapter.notifyDataSetChanged()

        val disposalTypeVisibility = if (notificationSettingsViewState.notificationEnabled) View.VISIBLE else View.GONE
        viewBinding.disposalTypes.visibility = disposalTypeVisibility
        viewBinding.introduction.visibility = disposalTypeVisibility
    }
}