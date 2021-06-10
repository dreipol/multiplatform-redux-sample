/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.ui

data class EnterZipViewState(
    val possibleZips: List<Int> = emptyList(),
    val selectedZip: Int? = null,
    val invalidZip: Boolean = false
) {
    val enterZipLabel = "onboarding_enter_zip_label"
    val filterEmptyText = "zip_invalid"
    val filteredZips: List<Int>
        get() = selectedZip?.let { zip -> possibleZips.filter { it.toString().startsWith(zip.toString()) } } ?: possibleZips
}