package ch.dreipol.multiplatform.reduxsample.shared.ui

interface OnboardingViewState {
    val step: Int
    val title: String
    val primary: String
}

data class OnboardingZipStep(
    override val step: Int,
    override val title: String,
    override val primary: String,
    val zips: List<String>
) : OnboardingViewState

data class GenericOnboardingStep(
    override val step: Int,
    override val title: String,
    override val primary: String
) : OnboardingViewState

interface OnboardingView : BaseView {
    fun render(viewState: OnboardingViewState)
    override fun presenter() = onboardingPresenter
}

val onboardingPresenter = presenter<OnboardingView> {
    {
        select({ it.onboardingViewState }) { render(state.onboardingViewState) }
    }
}