//
//  UIView+Shadow.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Nils Becker on 08.04.21.
//

import UIKit

extension UIView {
    func addDefaultShadow() {
        layer.shadowColor = UIColor.shadowColor.cgColor
        layer.shadowOpacity = 1
        layer.shadowOffset = CGSize(width: 0, height: 4)
        layer.shadowRadius = 2
    }
}
