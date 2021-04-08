//
//  UIView+Round.swift
//  Multiplatform Redux Sample
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
