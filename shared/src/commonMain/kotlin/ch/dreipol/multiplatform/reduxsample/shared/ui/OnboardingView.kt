package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingScreen

abstract class BaseOnboardingSubState {
    abstract val title: String
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
    override val title = "Zip"
    override val primaryEnabled
        get() = selectedZip != null
    override val closeEnabled
        get() = primaryEnabled
}

data class SelectDisposalTypesState(
    val showCarton: Boolean = false,
    val showBioWaste: Boolean = false,
    val showPaper: Boolean = false,
    val showETram: Boolean = false,
    val showCargoTram: Boolean = false,
    val showTextils: Boolean = false,
    val showHazardousWaste: Boolean = false,
    val showSweepings: Boolean = false
) : BaseOnboardingSubState() {
    companion object {
        fun fromSettings(settings: Settings): SelectDisposalTypesState {
            return SelectDisposalTypesState(
                settings.showCarton, settings.showBioWaste,
                settings.showPaper, settings.showETram, settings.showCargoTram, settings.showTextils,
                settings.showHazardousWaste, settings.showSweepings
            )
        }
    }

    override val title = "select disposaltypes"
}

class AddNotificationState : BaseOnboardingSubState() {
    override val title = "AddNotificationState"
}

class FinishState : BaseOnboardingSubState() {

    override val title = "Finish"
    override val primary = "finish"
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