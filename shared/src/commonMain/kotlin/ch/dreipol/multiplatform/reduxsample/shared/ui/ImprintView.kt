/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.ui

data class ImprintViewState(
    val headerViewState: HeaderViewState = HeaderViewState("settings_imprint"),
    val contentHtmlKey: String = "imprint_text"
)

interface ImprintView : BaseView {
    override fun presenter() = imprintPresenter

    fun render(imprintViewState: ImprintViewState)
}

val imprintPresenter = presenter<ImprintView> {
    {
        select({ it.settingsViewState.imprintViewState }) {
            val viewState = state.settingsViewState.imprintViewState
            render(viewState)
        }
    }
}