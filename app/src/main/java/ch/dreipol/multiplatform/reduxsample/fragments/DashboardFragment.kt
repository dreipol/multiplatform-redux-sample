package ch.dreipol.multiplatform.reduxsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentDashboardBinding
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.setNewZipThunk
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardView
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import ch.dreipol.multiplatform.reduxsample.utils.getDrawableIdentifier
import ch.dreipol.multiplatform.reduxsample.utils.getString
import ch.dreipol.multiplatform.reduxsample.utils.setNewText
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
        viewBinding.menu.setOnClickListener { rootDispatch(NavigationAction.DISPOSAL_TYPES) }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.scrollview.post { viewBinding.scrollview.scrollTo(0, viewBinding.title.top) }
    }

    override fun render(viewState: DashboardViewState) {
        viewBinding.notifications.setImageResource(requireContext().getDrawableIdentifier(viewState.notificationIcon))
        viewBinding.menu.setImageResource(requireContext().getDrawableIdentifier(viewState.menuIcon))
        viewBinding.title.text = requireContext().getString(viewState.titleKey)
        nextDisposalsAdapter.disposals = viewState.disposalsState.nextDisposals
        nextDisposalsAdapter.notifyDataSetChanged()
        disposalListAdapter.disposalNotification = viewState.disposalsState.disposals
        disposalListAdapter.buildGroupedData()
        disposalListAdapter.notifyDataSetChanged()
        viewBinding.zip.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val zip = textView.text?.toString()?.toIntOrNull() ?: return@setOnEditorActionListener true
                rootDispatch(setNewZipThunk(zip))
            }
            false
        }
        viewBinding.zip.setNewText(viewState.zip?.toString())
    }
}