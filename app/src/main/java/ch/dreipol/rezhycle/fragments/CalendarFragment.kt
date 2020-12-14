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
import ch.dreipol.rezhycle.view.DisposalListAdapter
import ch.dreipol.rezhycle.view.NextDisposalListAdapter

class CalendarFragment : BaseFragment<FragmentCalendarBinding, CalendarView>(), CalendarView {

    private lateinit var nextDisposalsAdapter: NextDisposalListAdapter
    private lateinit var disposalListAdapter: DisposalListAdapter

    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentCalendarBinding {
        return FragmentCalendarBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        nextDisposalsAdapter = NextDisposalListAdapter(emptyList(), requireContext())
        viewBinding.nextDisposals.adapter = nextDisposalsAdapter
        disposalListAdapter = DisposalListAdapter(emptyList(), requireContext())
        viewBinding.disposals.adapter = disposalListAdapter
        return root
    }

    override fun render(viewState: CalendarViewState) {
        viewBinding.title.text = String.format(requireContext().getString(viewState.titleReplaceable), viewState.zip)
        nextDisposalsAdapter.disposals = viewState.disposalsState.nextDisposals
        nextDisposalsAdapter.notifyDataSetChanged()
        disposalListAdapter.disposalCalendarEntry = viewState.disposalsState.disposals
        disposalListAdapter.buildGroupedData()
        disposalListAdapter.notifyDataSetChanged()
    }
}