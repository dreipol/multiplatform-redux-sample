package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingScreen

abstract class BaseOnboardingSubState {
    abstract val title: String?
    open val primary = "next"
    open val primaryEnabled = true
    open val primaryAction = NavigationAction.ONBOARDING_NEXT
    open val closeEnabled = true
}

data class OnboardingViewState(
    val enterZipState: EnterZipState = EnterZipState(),
    val selectDisposalTypesState: SelectDisposalTypesState = SelectDisposalTypesState(),
    val addNotificationState: AddNotificationState = AddNotificationState(),
    val finishState: FinishState = FinishState()
) {

    companion object {
        const val ONBOARDING_VIEW_COUNT = 4
    }

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

data class EnterZipState(
    val possibleZips: List<String> = emptyList(),
    val selectedZip: Int? = null
) : BaseOnboardingSubState() {
    override val title = "onboarding_1_title"
    override val primaryEnabled
        get() = selectedZip != null
    override val closeEnabled
        get() = primaryEnabled
    val enterZipLabel = "onboarding_enter_zip_label"
}

data class SelectDisposalTypesState(
    val selectedDisposalTypes: List<DisposalType> = emptyList()
) : BaseOnboardingSubState() {
    companion object {
        fun fromSettings(notificationSettings: NotificationSettings?): SelectDisposalTypesState {
            val selectedDisposalTypes = notificationSettings?.disposalTypes ?: emptyList()
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

data class AddNotificationState(val addNotification: Boolean = false) : BaseOnboardingSubState() {
    override val title = "onboarding_3_title"
}

class FinishState : BaseOnboardingSubState() {

    override val title: String? = null
    override val primary = "okay"
    override val primaryAction = NavigationAction.ONBOARDING_END
}

interface OnboardingView : BaseView {
    fun render(onboardingSubState: BaseOnboardingSubState)
    override fun presenter() = onboardingPresenter
}

val onboardingPresenter = presenter<OnboardingView> {
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