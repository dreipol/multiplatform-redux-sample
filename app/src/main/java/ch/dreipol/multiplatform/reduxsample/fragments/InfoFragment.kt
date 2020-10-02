package ch.dreipol.multiplatform.reduxsample.fragments

import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentInfoBinding
import ch.dreipol.multiplatform.reduxsample.shared.ui.InfoView

class InfoFragment : BaseFragment<FragmentInfoBinding, InfoView>(), InfoView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentInfoBinding {
        return FragmentInfoBinding.inflate(layoutInflater)
    }
}