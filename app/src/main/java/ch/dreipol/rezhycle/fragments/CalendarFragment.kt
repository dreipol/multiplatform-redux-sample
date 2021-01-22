package ch.dreipol.rezhycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.CalendarView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CalendarViewState
import ch.dreipol.rezhycle.databinding.FragmentCalendarBinding
import ch.dreipol.rezhycle.utils.getString
import ch.dreipol.rezhycle.view.CalendarHeaderModel
import ch.dreipol.rezhycle.view.CalendarListAdapter
import ch.dreipol.rezhycle.view.NextDisposalListAdapter

class CalendarFragment : BaseFragment<FragmentCalendarBinding, CalendarView>(), CalendarView {

    private lateinit var nextDisposalsAdapter: NextDisposalListAdapter
    private lateinit var calendarListAdapter: CalendarListAdapter

    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentCalendarBinding {
        return FragmentCalendarBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        nextDisposalsAdapter = NextDisposalListAdapter(emptyList(), requireContext())
        calendarListAdapter = CalendarListAdapter(CalendarHeaderModel("", nextDisposalsAdapter), emptyList(), requireContext())
        viewBinding.calendar.adapter = calendarListAdapter
        return root
    }

    override fun render(viewState: CalendarViewState) {
        val title = String.format(requireContext().getString(viewState.titleReplaceable), viewState.zip)
        if (nextDisposalsAdapter.disposals == viewState.disposalsState.nextDisposals &&
            calendarListAdapter.calendarHeaderModel.title == title &&
            calendarListAdapter.disposalCalendarEntry == viewState.disposalsState.disposals
        ) {
            // nothing changed
            return
        }
        nextDisposalsAdapter.disposals = viewState.disposalsState.nextDisposals
        calendarListAdapter.calendarHeaderModel = CalendarHeaderModel(title, nextDisposalsAdapter)
        calendarListAdapter.disposalCalendarEntry = viewState.disposalsState.disposals
        calendarListAdapter.buildGroupedData()
        calendarListAdapter.notifyDataSetChanged()
    }
}