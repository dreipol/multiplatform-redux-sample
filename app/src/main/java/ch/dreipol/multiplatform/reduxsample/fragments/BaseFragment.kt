package ch.dreipol.multiplatform.reduxsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseView
import com.github.dreipol.dreidroid.utils.AnimationHelper

abstract class BaseFragment<B : ViewBinding, V : BaseView> : Fragment() {
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

    internal abstract fun createBinding(): B
}