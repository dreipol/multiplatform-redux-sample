package ch.dreipol.multiplatform.reduxsample.shared.ui

data class EnterZipViewState(
    val possibleZips: List<Int> = emptyList(),
    val selectedZip: Int? = null,
    val invalidZip: Boolean = false,
    val canGoBack: Boolean = true,
) {
    val enterZipLabel = "onboarding_enter_zip_label"
    val filterEmptyText = "zip_invalid"
    val filteredZips: List<Int>
        get() = selectedZip?.let { zip -> possibleZips.filter { it.toString().startsWith(zip.toString()) } } ?: possibleZips
}