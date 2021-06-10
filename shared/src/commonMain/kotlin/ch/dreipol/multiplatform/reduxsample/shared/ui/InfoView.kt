/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.ui

data class InfoViewState(val titleHtmlKey: String = "info_title", val textHtmlKey: String = "info_text")

interface InfoView : BaseView {
    override fun presenter() = infoPresenter

    fun render(infoViewState: InfoViewState)
}

val infoPresenter = presenter<InfoView> {
    {
        select({ it.infoViewState }) { render(state.infoViewState) }
    }
}