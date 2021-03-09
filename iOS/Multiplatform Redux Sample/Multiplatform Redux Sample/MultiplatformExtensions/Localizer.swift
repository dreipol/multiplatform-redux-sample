//
//  Localizer.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 09.03.21.
//

import Foundation
import dreiKit
import ReduxSampleShared

class Localizer: Localize {
    func localize(string: String) -> String {
        string.localized
    }
}
