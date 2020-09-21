package ch.dreipol.multiplatform.reduxsample.shared.database

enum class DisposalType(val packageId: String) {
    CARTON("entsorgungskalender_karton"),
    BIO_WASTE("entsorgungskalender_gartenabfall"),
    PAPER("entsorgungskalender_papier"),
    E_TRAM("entsorgungskalender_eTram"),
    CARGO_TRAM("entsorgungskalender_cargotram"),
    TEXTILES("entsorgungskalender_textilien"),
    HAZARDOUS_WASTE("entsorgungskalender_sonderabfall"),
    SWEEPINGS("entsorgungskalender_kehricht"),
}