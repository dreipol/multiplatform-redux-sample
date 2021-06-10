//
//  UIButton+Brand.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Samuel Bichsel on 09.03.21.
//

import UIKit.UIButton

extension UIButton {
    class func createLink() -> UIButton {
        let button = UIButton(type: .custom).autolayout()
        button.titleLabel?.font = .link()
        button.contentHorizontalAlignment = .left
        button.setTitleColor(.accentDarkAccent, for: .normal)
        button.setTitleColor(.accentAccent, for: .highlighted)

        return button
    }
}
