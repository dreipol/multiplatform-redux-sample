//
//  Calendar+Swiss.swift
//  dreiKit
//
//  Created by Nils Becker on 27.11.20.
//

import Foundation

public extension Calendar {
    private static func cetWithLocale(identifier: String) -> Self {
        var calendar = Calendar(identifier: .gregorian)
        // swiftlint:disable:next force_unwrapping
        calendar.timeZone = TimeZone(identifier: "CET")!
        calendar.locale = Locale(identifier: identifier)
        return calendar
    }

    static func swissGerman() -> Calendar {
        return cetWithLocale(identifier: "de_CH")
    }

    static func swissFrench() -> Calendar {
        return cetWithLocale(identifier: "fr_CH")
    }

    static func swissItalian() -> Calendar {
        return cetWithLocale(identifier: "it_CH")
    }

    static func swissRomansh() -> Calendar {
        return cetWithLocale(identifier: "rm_CH")
    }
}
