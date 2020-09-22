package ch.dreipol.multiplatform.reduxsample.shared.ui

data class OnboardingViewState(
    val step: Int,
    val title: String,
    val primary: String,
    val onboardingZipStep: OnboardingZipStep
) {
    companion object {
        fun create(step: Int, fromExisting: OnboardingViewState? = null): OnboardingViewState {
            return when (step) {
                1 -> OnboardingViewState(step, "ZIP", "next", OnboardingZipStep(emptyList()))
                2, 3 -> OnboardingViewState(step, "step $step", "next", fromExisting?.onboardingZipStep ?: throw IllegalStateException())
                4 -> OnboardingViewState(step, "", "finish", fromExisting?.onboardingZipStep ?: throw IllegalStateException())
                else -> throw IllegalStateException()
            }
        }
    }
}

data class OnboardingZipStep(
    val zips: List<String>
)

interface OnboardingView : BaseView {
    fun render(viewState: OnboardingViewState)
    override fun presenter() = onboardingPresenter
}

val onboardingPresenter = presenter<OnboardingView> {
    {
        select({ it.onboardingViewState }) { render(state.onboardingViewState) }
    }
}