package ch.dreipol.rezhycle.fragments.settings

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.content.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun render(imprintViewState: ImprintViewState) {
        bindHeader(imprintViewState.headerViewState, viewBinding.header)
        viewBinding.content.text = Html.fromHtml(requireContext().getString(imprintViewState.contentHtmlKey))
    }
}