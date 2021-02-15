//
//  UIColor+Tools.swift
//  dreiKit
//
//  Created by Simon Müller on 09.09.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import UIKit

public extension UIColor {
    static var random: UIColor {
        return UIColor(red: .random(), green: .random(), blue: .random(), alpha: 1)
    }

    convenience init(hex: Int) {
        let blue = hex & 0x0000FF
        let green = ((hex & 0x00FF00) >> 8)
        let red = ((hex & 0xFF0000) >> 16)

        self.init(red: CGFloat(red/255), green: CGFloat(green/255), blue: CGFloat(blue/255), alpha: 1)
    }
}
