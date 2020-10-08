package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
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
    val showCarton: Boolean = false,
    val showBioWaste: Boolean = false,
    val showPaper: Boolean = false,
    val showETram: Boolean = false,
    val showCargoTram: Boolean = false,
    val showTextiles: Boolean = false,
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

        fun update(state: SelectDisposalTypesState, toUpdate: DisposalType, value: Boolean): SelectDisposalTypesState {
            return when (toUpdate) {
                DisposalType.CARTON -> state.copy(showCarton = value)
                DisposalType.BIO_WASTE -> state.copy(showBioWaste = value)
                DisposalType.PAPER -> state.copy(showPaper = value)
                DisposalType.E_TRAM -> state.copy(showETram = value)
                DisposalType.CARGO_TRAM -> state.copy(showCargoTram = value)
                DisposalType.TEXTILES -> state.copy(showTextiles = value)
                DisposalType.HAZARDOUS_WASTE -> state.copy(showHazardousWaste = value)
                DisposalType.SWEEPINGS -> state.copy(showSweepings = value)
            }
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