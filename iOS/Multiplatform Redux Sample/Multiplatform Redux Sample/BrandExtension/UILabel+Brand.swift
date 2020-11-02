//
//  UILabel+Brand.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 02.11.20.
//

import Foundation
import UIKit.UILabel

extension UILabel {
    //TODO add other label styles
//    static func h1() -> UILabel {
//        let label = UILabel.autoLayout()
//        label.font = .h1()
//        label.textColor = .white
//        label.textAlignment = .center
//        label.numberOfLines = 0
//        return label
//    }
//
//    static func copy1() -> UILabel {
//        let label = UILabel.autoLayout()
//        label.font = .copy1Middle()
//        label.textColor = .white
//        label.textAlignment = .center
//        label.numberOfLines = 0
//        return label
//    }
//
//    static func lead() -> UILabel {
//        let label = UILabel.autoLayout()
//        label.font = .lead()
//        label.textColor = .white
//        label.textAlignment = .center
//        label.numberOfLines = 0
//        return label
//    }
//
//    static func textButton() -> UILabel {
//        let label = UILabel.autoLayout()
//        label.font = .textLink()
//        label.textColor = .primaryBlue
//        label.textAlignment = .center
//        label.numberOfLines = 1
//        return label
//    }

    static func button() -> UILabel {
        let label = UILabel.autoLayout() // Currently UILabel to be ignored when changing appearance
        label.font = .button()
        label.textColor = .white
        label.textAlignment = .center
        label.numberOfLines = 1
        return label
    }
}
