package ch.dreipol.rezhycle.fragments.settings

import android.text.Html
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.ImprintView
import ch.dreipol.multiplatform.reduxsample.shared.ui.ImprintViewState
import ch.dreipol.rezhycle.databinding.FragmentImprintBinding
import ch.dreipol.rezhycle.fragments.BaseFragment
import ch.dreipol.rezhycle.utils.getString

class ImprintFragment : BaseFragment<FragmentImprintBinding, ImprintView>(), ImprintView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentImprintBinding {
        return FragmentImprintBinding.inflate(layoutInflater)
    }

    override fun render(imprintViewState: ImprintViewState) {
        bindHeader(imprintViewState.headerViewState, viewBinding.header)
        viewBinding.content.text = Html.fromHtml(requireContext().getString(imprintViewState.contentHtmlKey))
    }

}