package ch.dreipol.multiplatform.reduxsample.shared.database

enum class DisposalType(val packageId: String, val translationKey: String, val iconId: String) {
    CARTON("entsorgungskalender_karton", "disposal_types_carton", "ic_24_carton_colored"),
    BIO_WASTE("entsorgungskalender_gartenabfall", "disposal_types_bio_waste", "ic_24_bio_colored"),
    PAPER("entsorgungskalender_papier", "disposal_types_paper", "ic_24_paper_colored"),
    E_TRAM("entsorgungskalender_eTram", "disposal_types_e_tram", "ic_24_electro_colored"),
    CARGO_TRAM("entsorgungskalender_cargotram", "disposal_types_cargo_tram", "ic_24_glas_colored"),
    TEXTILES("entsorgungskalender_textilien", "disposal_types_textiles", "ic_24_cloth_colored"),
    HAZARDOUS_WASTE("entsorgungskalender_sonderabfall", "disposal_types_hazardous_waste", "ic_24_oil_colored"),
    SWEEPINGS("entsorgungskalender_kehricht", "disposal_types_sweepings", "ic_24_trash_colored"),
}