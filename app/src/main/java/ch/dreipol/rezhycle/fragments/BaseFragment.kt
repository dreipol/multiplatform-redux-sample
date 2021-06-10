/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.fragments

import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.uiDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseView
import ch.dreipol.multiplatform.reduxsample.shared.ui.HeaderViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.ViewHeaderBinding
import ch.dreipol.rezhycle.hideKeyboard
import ch.dreipol.rezhycle.utils.getDrawableIdentifier
import ch.dreipol.rezhycle.utils.getString
import com.github.dreipol.dreidroid.utils.AnimationHelper
import com.github.dreipol.dreidroid.utils.ViewUtils
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
        updateSystemBarColor(overrideSystemBarColor())
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

    open fun overrideSystemBarColor(): SystemBarColor? {
        return null
    }

    fun bindHeader(headerViewState: HeaderViewState, viewHeaderBinding: ViewHeaderBinding) {
        ViewUtils.useTouchDownListener(viewHeaderBinding.iconLeft, viewHeaderBinding.iconLeft)
        viewHeaderBinding.iconLeft.setOnClickListener { requireActivity().onBackPressed() }
        viewHeaderBinding.iconLeft.setImageResource(requireContext().getDrawableIdentifier(headerViewState.iconLeft))
        viewHeaderBinding.iconLeft.contentDescription = requireContext().getString(headerViewState.backCDKey)
        viewHeaderBinding.title.text = requireContext().getString(headerViewState.title)
    }

    internal abstract fun createBinding(): B
}

fun Fragment.updateSystemBarColor(systemBarColor: SystemBarColor?) {
    if (systemBarColor != null) {
        val window = requireActivity().window
        window.statusBarColor = resources.getColor(systemBarColor.color, null)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            setSystemBarColorAPI30(systemBarColor, window)
        } else {
            setSystemBarColorBelowAPI30(systemBarColor, window)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
private fun setSystemBarColorAPI30(systemBarColor: SystemBarColor, window: Window) {
    if (systemBarColor.light) {
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
        )
    } else {
        window.insetsController?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
    }
}

@Suppress("DEPRECATION")
private fun setSystemBarColorBelowAPI30(systemBarColor: SystemBarColor, window: Window) {
    if (systemBarColor.light) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        window.decorView.systemUiVisibility = 0
    }
}

interface KeyboardUsingFragment

enum class SystemBarColor(@ColorRes val color: Int, val light: Boolean) {
    DARK(R.color.primary_dark, false),
    LIGHT(R.color.primary_light, true),
    WHITE(R.color.white, true),
}