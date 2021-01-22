package ch.dreipol.rezhycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.uiDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseView
import ch.dreipol.multiplatform.reduxsample.shared.ui.HeaderViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import ch.dreipol.rezhycle.databinding.ViewHeaderBinding
import ch.dreipol.rezhycle.hideKeyboard
import ch.dreipol.rezhycle.utils.getDrawableIdentifier
import ch.dreipol.rezhycle.utils.getString
import com.github.dreipol.dreidroid.utils.AnimationHelper
import kotlinx.coroutines.CoroutineScope

abstract class BaseFragment<B : ViewBinding, V : BaseView> : Fragment(), BaseView {
    internal abstract val presenterObserver: PresenterLifecycleObserver
    lateinit var viewBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycle.addObserver(presenterObserver)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = createBinding()
        viewBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // call subscriber to trigger initial view update
        val subscriber = presenter()(this, CoroutineScope(uiDispatcher))(getAppConfiguration().reduxSampleApp.store)
        subscriber()
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        val forcedAnimation: Animation? = if (enter) {
            AnimationHelper.getNextFragmentEnterAnimation()
        } else {
            AnimationHelper.getNextFragmentExitAnimation()
        }

        forcedAnimation?.let {
            return it
        }

        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onDetach() {
        super.onDetach()
        if (this is KeyboardUsingFragment) {
            activity?.hideKeyboard()
        }
    }

    fun bindHeader(headerViewState: HeaderViewState, viewHeaderBinding: ViewHeaderBinding) {
        viewHeaderBinding.iconLeft.setOnClickListener { requireActivity().onBackPressed() }
        viewHeaderBinding.iconLeft.setImageResource(requireContext().getDrawableIdentifier(headerViewState.iconLeft))
        viewHeaderBinding.title.text = requireContext().getString(headerViewState.title)
    }

    internal abstract fun createBinding(): B
}

interface KeyboardUsingFragment