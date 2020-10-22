package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingScreen

abstract class BaseOnboardingSubState {
    abstract val title: String?
    open val primary = "next"
    open val primaryEnabled = true
    open val primaryAction = NavigationAction.ONBOARDING_NEXT
}

data class OnboardingViewState(
    val enterZipState: EnterZipState = EnterZipState(),
    val selectDisposalTypesState: SelectDisposalTypesState = SelectDisposalTypesState(),
    val addNotificationState: AddNotificationState = AddNotificationState(),
    val finishState: FinishState = FinishState(),
) {
    val closeEnabled: Boolean
        get() = enterZipState.primaryEnabled && selectDisposalTypesState.primaryEnabled && addNotificationState.primaryEnabled
    val onboardingViewCount: Int
        get() = if (closeEnabled) 4 else 1
    val dotIndicatorsVisible: Boolean
        get() = onboardingViewCount > 1

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
    val enterZipLabel = "onboarding_enter_zip_label"
}

data class SelectDisposalTypesState(
    val selectedDisposalTypes: List<DisposalType> = DisposalType.values().toList()
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
    val addNotification: Boolean = false,
    val remindTimes: List<Pair<RemindTime, Boolean>> = RemindTime.values()
        .map { if (RemindTime.EVENING_BEFORE == it) it to true else it to false }
) :
    BaseOnboardingSubState() {
    override val title = "onboarding_3_title"
}

class FinishState : BaseOnboardingSubState() {

    override val title: String? = null
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