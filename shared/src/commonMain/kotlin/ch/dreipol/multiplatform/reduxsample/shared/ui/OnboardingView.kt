package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings

data class OnboardingViewState(
    val step: Int,
    val title: String,
    val primary: String,
    val onboardingZipStep: OnboardingZipStep,
    val onboardingSelectDisposalTypes: OnboardingSelectDisposalTypes
) {
    companion object {
        fun create(step: Int, fromExisting: OnboardingViewState? = null): OnboardingViewState {
            return when (step) {
                1 ->
                    OnboardingViewState(
                        step, "ZIP", "next", fromExisting?.onboardingZipStep ?: OnboardingZipStep(emptyList()),
                        fromExisting?.onboardingSelectDisposalTypes ?: OnboardingSelectDisposalTypes()
                    )
                2, 3 ->
                    OnboardingViewState(
                        step, "step $step", "next", fromExisting?.onboardingZipStep ?: throw IllegalStateException(),
                        fromExisting.onboardingSelectDisposalTypes
                    )
                4 ->
                    OnboardingViewState(
                        step, "", "finish", fromExisting?.onboardingZipStep ?: throw IllegalStateException(),
                        fromExisting.onboardingSelectDisposalTypes
                    )
                else -> throw IllegalStateException()
            }
        }
    }
}

data class OnboardingZipStep(
    val possibleZips: List<String>,
    val selectedZip: String? = null
)

data class OnboardingSelectDisposalTypes(
    val showCarton: Boolean = false,
    val showBioWaste: Boolean = false,
    val showPaper: Boolean = false,
    val showETram: Boolean = false,
    val showCargoTram: Boolean = false,
    val showTextils: Boolean = false,
    val showHazardousWaste: Boolean = false,
    val showSweepings: Boolean = false
) {
    companion object {
        fun fromSettings(settings: Settings): OnboardingSelectDisposalTypes {
            return OnboardingSelectDisposalTypes(
                settings.showCarton, settings.showBioWaste,
                settings.showPaper, settings.showETram, settings.showCargoTram, settings.showTextils,
                settings.showHazardousWaste, settings.showSweepings
            )
        }
    }
}

interface OnboardingView : BaseView {
    fun render(viewState: OnboardingViewState)
    override fun presenter() = onboardingPresenter
}

val onboardingPresenter = presenter<OnboardingView> {
    {
        select({ it.onboardingViewState }) { render(state.onboardingViewState) }
    }
}