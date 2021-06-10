//
//  UIView+Round.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 26.11.20.
//

import UIKit

extension UIView {
    func makeCircular(radius: CGFloat, color: CGColor) {
        self.layer.masksToBounds = false
        self.layer.backgroundColor = color
        self.layer.cornerRadius = radius
        self.clipsToBounds = true
        let size = radius * 2
        self.widthAnchor.constraint(equalToConstant: size).isActive = true
        self.heightAnchor.constraint(equalToConstant: size).isActive = true
    }
}
