package ch.dreipol.multiplatform.reduxsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentDashboardBinding
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardView
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import ch.dreipol.multiplatform.reduxsample.utils.getString
import ch.dreipol.multiplatform.reduxsample.view.DisposalListAdapter
import ch.dreipol.multiplatform.reduxsample.view.NextDisposalListAdapter

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardView>(), DashboardView {

    private lateinit var nextDisposalsAdapter: NextDisposalListAdapter
    private lateinit var disposalListAdapter: DisposalListAdapter

    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        nextDisposalsAdapter = NextDisposalListAdapter(emptyList(), requireContext())
        viewBinding.nextDisposals.adapter = nextDisposalsAdapter
        disposalListAdapter = DisposalListAdapter(emptyList(), requireContext())
        viewBinding.disposals.adapter = disposalListAdapter
        return root
    }

    override fun render(viewState: DashboardViewState) {
        viewBinding.title.text = requireContext().getString(viewState.titleKey)
        nextDisposalsAdapter.disposals = viewState.disposalsState.nextDisposals
        nextDisposalsAdapter.notifyDataSetChanged()
        disposalListAdapter.disposalNotification = viewState.disposalsState.disposals
        disposalListAdapter.buildGroupedData()
        disposalListAdapter.notifyDataSetChanged()
    }
}