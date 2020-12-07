package ch.dreipol.multiplatform.reduxsample.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.R
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentNotificationSettingsBinding
import ch.dreipol.multiplatform.reduxsample.fragments.BaseFragment
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.redux.addOrRemoveNotificationThunk
import ch.dreipol.multiplatform.reduxsample.shared.redux.setRemindTimeThunk
import ch.dreipol.multiplatform.reduxsample.shared.ui.NotificationSettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.NotificationSettingsViewState
import ch.dreipol.multiplatform.reduxsample.utils.getString
import ch.dreipol.multiplatform.reduxsample.view.NotificationListAdapter
import ch.dreipol.multiplatform.reduxsample.view.NotificationListTheme
import ch.dreipol.multiplatform.reduxsample.view.SelectDisposalTypesAdapter

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
            requireContext(), emptyList(), true, NotificationListTheme.WHITE,
            { remindTime ->
                rootDispatch(setRemindTimeThunk(remindTime))
            },
            {
                rootDispatch(addOrRemoveNotificationThunk())
            }
        )
        viewBinding.notification.adapter = notificationAdapter
        disposalTypeAdapter = SelectDisposalTypesAdapter(
            requireContext(), emptyMap(), R.color.test_app_blue,
            { _, disposalType ->
                rootDispatch(addOrRemoveNotificationThunk(disposalType))
            }
        )
        viewBinding.disposalTypes.adapter = disposalTypeAdapter
        return view
    }

    override fun render(notificationSettingsViewState: NotificationSettingsViewState, notificationSettings: List<NotificationSettings>?) {
        bindHeader(notificationSettingsViewState.headerViewState, viewBinding.header)
        viewBinding.description.text = requireContext().getString(notificationSettingsViewState.descriptionKey)
        val notification = notificationSettings?.firstOrNull()
        val notificationEnabled = notification != null
        val remindTime = notification?.remindTime ?: SettingsDataStore.defaultRemindTime
        notificationAdapter.notificationEnabled = notificationEnabled
        notificationAdapter.remindTimes = RemindTime.values().map { if (remindTime == it) it to true else it to false }
        notificationAdapter.buildGroupedData()
        notificationAdapter.notifyDataSetChanged()

        notification?.disposalTypes?.let { disposalTypes ->
            disposalTypeAdapter.disposalTypes =
                DisposalType.values().map { if (disposalTypes.contains(it)) it to true else it to false }.toMap()
            disposalTypeAdapter.notifyDataSetChanged()
        }
        val disposalTypeVisibility = if (notificationEnabled) View.VISIBLE else View.GONE
        viewBinding.disposalTypes.visibility = disposalTypeVisibility
        viewBinding.description.visibility = disposalTypeVisibility
    }
}