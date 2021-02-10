//
//  UIColor+Brand.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 26.10.20.
//

import UIKit

extension UIColor {

    @nonobjc class var primaryDark: UIColor {
      return UIColor(red: 44.0 / 255.0, green: 102.0 / 255.0, blue: 100.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var monochromesGrey: UIColor {
      return UIColor(red: 120.0 / 255.0, green: 120.0 / 255.0, blue: 128.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var monochromesDarkGrey: UIColor {
      return UIColor(white: 84.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var monochromesGreyLight: UIColor {
      return UIColor(white: 177.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var signalError: UIColor {
      return UIColor(red: 1.0, green: 12.0 / 255.0, blue: 62.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var primaryPale: UIColor {
      return UIColor(red: 114.0 / 255.0, green: 172.0 / 255.0, blue: 170.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var primaryPrimary: UIColor {
      return UIColor(red: 180.0 / 255.0, green: 228.0 / 255.0, blue: 226.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var secondaryLight: UIColor {
      return UIColor(red: 183.0 / 255.0, green: 1.0, blue: 225.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var primaryLight: UIColor {
      return UIColor(red: 222.0 / 255.0, green: 248.0 / 255.0, blue: 246.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var secondarySecondary: UIColor {
      return UIColor(red: 135.0 / 255.0, green: 1.0, blue: 205.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var accentAccent: UIColor {
      return UIColor(red: 1.0, green: 136.0 / 255.0, blue: 143.0 / 255.0, alpha: 1.0)
    }

    @nonobjc class var accentDarkAccent: UIColor {
      return UIColor(red: 194.0 / 255.0, green: 74.0 / 255.0, blue: 110.0 / 255.0, alpha: 1.0)
    }

}
