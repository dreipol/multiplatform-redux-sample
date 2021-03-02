//
//  String+Tools.swift
//  dreiKit
//
//  Created by Simon Müller on 09.09.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import Foundation

public extension String {
    var localized: String {
        return NSLocalizedString(self, tableName: nil, bundle: Bundle.main, value: "", comment: "")
    }

    var isLocalized: Bool {
        return self != localized
    }

    var localizedOptional: String? {
        return isLocalized ? localized : nil
    }

    func localized(_ args: CVarArg...) -> String {
        String(format: self.localized, arguments: args)
    }

    func removingWhitespace() -> String {
        return filter { !$0.isWhitespace }
    }

    func universalDecimal() -> Double? {
        let pointFormatter = NumberFormatter()
        pointFormatter.decimalSeparator = "."
        let commaFormatter = NumberFormatter()
        commaFormatter.decimalSeparator = ","
        var decimalValue = pointFormatter.number(from: self)?.doubleValue
        if decimalValue == nil {
            decimalValue = commaFormatter.number(from: self)?.doubleValue
        }
        return decimalValue
    }
}
