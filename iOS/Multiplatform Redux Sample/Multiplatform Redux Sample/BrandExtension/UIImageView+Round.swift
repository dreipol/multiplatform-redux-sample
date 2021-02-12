//
//  UIImageView+Round.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 26.11.20.
//

import UIKit

extension UIImageView {
    func makeRound(height: CGFloat) {
        self.layer.masksToBounds = false
        self.layer.backgroundColor = UIColor.primaryLight.cgColor
        self.layer.cornerRadius = height/2
        self.clipsToBounds = true
        self.widthAnchor.constraint(equalToConstant: height).isActive = true
        self.heightAnchor.constraint(equalToConstant: height).isActive = true
    }
}
