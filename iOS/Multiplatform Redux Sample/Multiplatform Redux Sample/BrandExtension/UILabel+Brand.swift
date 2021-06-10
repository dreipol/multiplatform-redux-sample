//
//  UILabel+Brand.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 02.11.20.
//

import Foundation
import UIKit.UILabel

extension UILabel {

    static func h2() -> UILabel {
        let label = UILabel.autoLayout()
        label.font = .h2()
        label.textColor = .primaryDark
        label.textAlignment = .center
        label.numberOfLines = 0
        return label
    }

    static func h3() -> UILabel {
        let label = UILabel.autoLayout()
        label.font = .h3()
        label.textColor = .primaryDark
        label.textAlignment = .center
        label.numberOfLines = 0
        return label
    }

    static func h4() -> UILabel {
        let label = UILabel.autoLayout()
        label.font = .h4()
        label.textColor = .primaryDark
        label.textAlignment = .center
        label.numberOfLines = 0
        return label
    }

    static func h5() -> UILabel {
        let label = UILabel.autoLayout()
        label.font = .h5()
        label.textColor = .primaryDark
        label.textAlignment = .left
        label.numberOfLines = 0
        return label
    }

    static func label() -> UILabel {
        let label = UILabel.autoLayout()
        label.font = .label()
        label.textColor = .white
        label.textAlignment = .center
        label.numberOfLines = 0
        return label
    }

    static func paragraph1() -> UILabel {
        let label = UILabel.autoLayout()
        label.font = .paragraph1()
        label.textColor = .primaryDark
        label.textAlignment = .left
        label.numberOfLines = 0
        return label
    }

    static func paragraph2() -> UILabel {
        let label = UILabel.autoLayout()
        label.font = .paragraph2()
        label.textColor = .primaryDark
        label.textAlignment = .left
        label.numberOfLines = 0
        return label
    }

    static func button() -> UILabel {
        let label = UILabel.autoLayout()
        label.font = .button()
        label.textColor = .primaryDark
        label.textAlignment = .center
        label.numberOfLines = 1
        return label
    }
}
