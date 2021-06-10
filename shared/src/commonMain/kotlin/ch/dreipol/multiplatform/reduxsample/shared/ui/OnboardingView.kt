/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.redux.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction

abstract class BaseOnboardingSubState {
    abstract val title: String?
    open val primary = "next"
    open val primaryEnabled = true
    open val primaryAction = NavigationAction.ONBOARDING_NEXT
}

data class OnboardingViewState(
    val enterZipState: EnterZipOnboardingState = EnterZipOnboardingState(),
    val selectDisposalTypesState: SelectDisposalTypesState = SelectDisposalTypesState(),
    val addNotificationState: AddNotificationState = AddNotificationState(),
    val finishState: FinishState = FinishState(),
    val currentStep: Int = 1,
    val closeCDKey: String = "onboarding_close_contentdescription",
    val backCDKey: String = "back_contentdescription",
) {
    val closeEnabled: Boolean
        get() = enterZipState.primaryEnabled && selectDisposalTypesState.primaryEnabled && addNotificationState.primaryEnabled
    val canSwipe: Boolean
        get() = enterZipState.primaryEnabled
    val onboardingViewCount = 4
    val canGoBack: Boolean
        get() = currentStep != 1

    fun subStateFor(step: Int): BaseOnboardingSubState {
        return when (step) {
            1 -> enterZipState
            2 -> selectDisposalTypesState
            3 -> addNotificationState
            4 -> finishState
            else -> throw IllegalArgumentException()
        }
    }
}

data class EnterZipOnboardingState(
    val enterZipViewState: EnterZipViewState = EnterZipViewState()
) : BaseOnboardingSubState() {
    override val title = "onboarding_1_title"
    override val primaryEnabled
        get() = enterZipViewState.selectedZip != null && enterZipViewState.invalidZip.not()
}

data class SelectDisposalTypesState(
    val selectedDisposalTypes: List<DisposalType> = SettingsDataStore.defaultShownDisposalTypes,
    val disposalToggleCDReplaceableKey: String = "disposal_toggle_contentdescription",
    val disposalImageCDReplaceableKey: String = "disposal_image_contentdescription",
) : BaseOnboardingSubState() {
    companion object {
        fun fromSettings(settings: Settings): SelectDisposalTypesState {
            val selectedDisposalTypes = settings.showDisposalTypes
            return SelectDisposalTypesState(selectedDisposalTypes)
        }

        fun update(state: SelectDisposalTypesState, toUpdate: DisposalType, value: Boolean): SelectDisposalTypesState {
            val selectedDisposalTypes = state.selectedDisposalTypes.toMutableList()
            if (value) {
                if (selectedDisposalTypes.contains(toUpdate).not()) selectedDisposalTypes.add(toUpdate)
            } else {
                selectedDisposalTypes.remove(toUpdate)
            }
            return state.copy(selectedDisposalTypes = selectedDisposalTypes)
        }
    }

    override val title = "onboarding_2_title"
}

data class AddNotificationState(
    val addNotification: Boolean = true,
    val remindTimes: List<Pair<RemindTime, Boolean>> = RemindTime.values()
        .map { if (SettingsDataStore.defaultRemindTime == it) it to true else it to false },
    val notificationToggleCDKey: String = "notification_toggle_contentdescription",
    val checkIconCDKey: String = "check_icon_contentdescription",
) :
    BaseOnboardingSubState() {
    override val title = "onboarding_3_title"
}

data class FinishState(val animationCDKey: String = "onboarding_finish_animation_contentdescription") : BaseOnboardingSubState() {

    override val title: String = "onboarding_4_title"
    override val primary = "okay"
    override val primaryAction = NavigationAction.ONBOARDING_END
}

interface OnboardingView : BaseView {
    fun render(onboardingViewState: OnboardingViewState)
    override fun presenter() = onboardingPresenter
}

val onboardingPresenter = presenter<OnboardingView> {
    {
        select({ it.onboardingViewState }) {
            render(state.onboardingViewState)
        }
    }
}

interface OnboardingSubView : BaseView {
    fun render(onboardingSubState: BaseOnboardingSubState)
    override fun presenter() = onboardingSubPresenter
}

val onboardingSubPresenter = presenter<OnboardingSubView> {
    {
        val renderIfOnboarding = {
            val screen = state.navigationState.currentScreen as? OnboardingScreen
            screen?.let {
                render(state.onboardingViewState.subStateFor(it.step))
            }
        }
        select({ it.onboardingViewState }) {
            renderIfOnboarding()
        }

        select({ it.navigationState }) {
            if (state.navigationState.navigationDirection == NavigationDirection.POP) {
                renderIfOnboarding()
            }
        }
    }
}