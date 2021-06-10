/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.CalendarView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CalendarViewState
import ch.dreipol.rezhycle.databinding.FragmentCalendarBinding
import ch.dreipol.rezhycle.utils.getString
import ch.dreipol.rezhycle.view.CalendarHeaderModel
import ch.dreipol.rezhycle.view.CalendarListAdapter
import ch.dreipol.rezhycle.view.NextDisposalListAdapter

class CalendarFragment : BaseFragment<FragmentCalendarBinding, CalendarView>(), CalendarView {

    private lateinit var nextDisposalsAdapter: NextDisposalListAdapter
    private lateinit var calendarListAdapter: CalendarListAdapter

    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentCalendarBinding {
        return FragmentCalendarBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        nextDisposalsAdapter = NextDisposalListAdapter(emptyList(), requireContext())
        calendarListAdapter = CalendarListAdapter(CalendarHeaderModel("", nextDisposalsAdapter), emptyList(), "", "", requireContext())
        viewBinding.calendar.adapter = calendarListAdapter
        return root
    }

    override fun render(viewState: CalendarViewState) {
        val title = String.format(requireContext().getString(viewState.titleReplaceable), viewState.zip)
        if (nextDisposalsAdapter.disposals == viewState.disposalsState.nextDisposals &&
            calendarListAdapter.calendarHeaderModel.title == title &&
            calendarListAdapter.disposalCalendarEntry == viewState.disposalsState.disposals
        ) {
            // nothing changed
            return
        }
        nextDisposalsAdapter.disposals = viewState.disposalsState.nextDisposals
        nextDisposalsAdapter.disposalImageCDReplaceable = viewState.disposalImageCDReplaceable
        calendarListAdapter.calendarHeaderModel = CalendarHeaderModel(title, nextDisposalsAdapter)
        calendarListAdapter.disposalCalendarEntry = viewState.disposalsState.disposals
        calendarListAdapter.bellCDReplaceable = requireContext().getString(viewState.bellCDReplaceable)
        calendarListAdapter.disposalImageCDReplaceable = requireContext().getString(viewState.disposalImageCDReplaceable)
        calendarListAdapter.buildGroupedData()
        calendarListAdapter.notifyDataSetChanged()
    }
}