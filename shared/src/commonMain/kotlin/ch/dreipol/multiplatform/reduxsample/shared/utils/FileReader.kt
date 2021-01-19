package ch.dreipol.multiplatform.reduxsample.shared.utils

expect class FileReader {
    // imports the file from https://www.stadt-zuerich.ch/geodaten/download/Sammelstelle?format=10009
    fun readCollectionPointsFile(): String
}