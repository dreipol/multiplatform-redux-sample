//
//  UIFont+Brand.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 02.11.20.
//

import Foundation
import UIKit.UILabel

extension UIFont {

    static func h1() -> UIFont {
        return font(name: "OpenSans-Regular", size: 28.0)
    }

    static func h2() -> UIFont {
        return font(name: "OpenSans-Regular", size: 22.0)
    }

    static func h3() -> UIFont {
        return font(name: "OpenSans-Regular", size: 18.0)
    }

    static func h4() -> UIFont {
        return font(name: "OpenSans-SemiBold", size: 16.0)
    }

    static func h5() -> UIFont {
        return font(name: "OpenSans-Bold", size: 14.0)
    }

    static func label() -> UIFont {
        return font(name: "OpenSans-Bold", size: 14.0)
    }

    static func inputLabel() -> UIFont {
        return font(name: "OpenSans-Bold", size: 18.0)
    }

    static func paragraph1() -> UIFont {
        return font(name: "OpenSans-Regular", size: 16.0)
    }

    static func paragraph2() -> UIFont {
        return font(name: "OpenSans-Regular", size: 14.0)
    }

    static func button() -> UIFont {
        return font(name: "OpenSans-Bold", size: 16.0)
    }

    private static func font(name: String, size: CGFloat) -> UIFont {
        guard let font = UIFont(name: name, size: size) else {
            fatalError("Could not find correct font")
        }
        return font
    }

}
