/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.fragments

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.dreipol.rezhycle.R
import com.mikepenz.aboutlibraries.LibsFragmentCompat

open class LicenceFragment : Fragment() {

    private val libsFragmentCompat: LibsFragmentCompat = LibsFragmentCompat()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        updateSystemBarColor(SystemBarColor.WHITE)
        val aboutLibrariesInflater = inflater.cloneInContext(ContextThemeWrapper(inflater.context, R.style.AboutLibrariesTheme))
        return libsFragmentCompat.onCreateView(
            aboutLibrariesInflater.context, aboutLibrariesInflater, container, savedInstanceState,
            arguments
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libsFragmentCompat.onViewCreated(view)
    }

    override fun onDestroyView() {
        libsFragmentCompat.onDestroyView()
        super.onDestroyView()
    }
}