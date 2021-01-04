package ch.dreipol.multiplatform.reduxsample.shared.database

enum class DisposalType(val packageId: String, val translationKey: String, val notificationKey: String, val iconId: String) {
    CARTON("e8be896b-8aea-40b7-b042-961273576cd3", "disposal_types_carton", "notification_carton", "ic_24_carton_colored"),
    BIO_WASTE("e785a87c-0233-47e9-9a1a-32034e82f519", "disposal_types_bio_waste", "notification_bio_waste", "ic_24_bio_colored"),
    PAPER("266fe85f-3ae0-466a-b6f5-2a8e663893cc", "disposal_types_paper", "notification_paper", "ic_24_paper_colored"),
    E_TRAM("88a9bb1b-65db-4b30-a74a-188b0a61b3da", "disposal_types_e_tram", "notification_e_tram", "ic_24_electro_colored"),
    CARGO_TRAM("43f4613a-f0c2-4036-8902-77a784bde746", "disposal_types_cargo_tram", "notification_cargo_tram", "ic_24_glas_colored"),
    TEXTILES("a47e92c9-8e0a-454d-8c4e-2e4d7f6c87b3", "disposal_types_textiles", "notification_textiles", "ic_24_cloth_colored"),
    HAZARDOUS_WASTE(
        "2886fe2d-9acf-48c3-8414-d4ee6af7460a", "disposal_types_hazardous_waste", "notification_hazardous_waste",
        "ic_24_oil_colored"
    ),
    SWEEPINGS("ddc5c2fd-c730-4d55-a88c-69bbe6d5a37e", "disposal_types_sweepings", "notification_sweepings", "ic_24_trash_colored"),
}