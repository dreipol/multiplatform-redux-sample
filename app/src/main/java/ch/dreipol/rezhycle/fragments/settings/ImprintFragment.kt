/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

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
import ch.dreipol.rezhycle.fragments.SystemBarColor
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

    override fun overrideSystemBarColor(): SystemBarColor {
        return SystemBarColor.WHITE
    }

    override fun render(imprintViewState: ImprintViewState) {
        bindHeader(imprintViewState.headerViewState, viewBinding.header)
        viewBinding.content.text = Html.fromHtml(requireContext().getString(imprintViewState.contentHtmlKey))
    }
}