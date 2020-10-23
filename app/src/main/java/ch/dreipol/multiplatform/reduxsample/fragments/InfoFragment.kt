package ch.dreipol.multiplatform.reduxsample.fragments

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentInfoBinding
import ch.dreipol.multiplatform.reduxsample.shared.ui.InfoView
import ch.dreipol.multiplatform.reduxsample.shared.ui.InfoViewState
import ch.dreipol.multiplatform.reduxsample.utils.getString

class InfoFragment : BaseFragment<FragmentInfoBinding, InfoView>(), InfoView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentInfoBinding {
        return FragmentInfoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        viewBinding.title.movementMethod = LinkMovementMethod.getInstance()
        viewBinding.text.movementMethod = LinkMovementMethod.getInstance()
        return view
    }

    override fun render(infoViewState: InfoViewState) {
        viewBinding.title.text = Html.fromHtml(requireContext().getString(infoViewState.titleHtmlKey))
        viewBinding.text.text = Html.fromHtml(requireContext().getString(infoViewState.textHtmlKey))
    }
}