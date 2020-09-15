package ch.dreipol.multiplatform.reduxsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentDashboardBinding
import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardView
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardView>(), DashboardView {

    override val presenterObserver = PresenterLifecycleObserver(this)

    private var disposals = emptyList<Disposal>()

    override fun createBinding(): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        viewBinding.disposals.adapter = DisposalAdapter { disposals }
        return root
    }

    override fun render(viewState: DashboardViewState) {
        disposals = viewState.disposalsState.disposals
        viewBinding.disposals.adapter?.notifyDataSetChanged()
    }
}

class DisposalAdapter(val disposals: () -> List<Disposal>) : RecyclerView.Adapter<DisposalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisposalViewHolder {
        return DisposalViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: DisposalViewHolder, position: Int) {
        val disposals = disposals.invoke()
        holder.textView.text = "${disposals[position].zip} : ${disposals[position].date}"
    }

    override fun getItemCount(): Int {
        return disposals.invoke().size
    }
}

class DisposalViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)