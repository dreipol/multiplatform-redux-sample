//
//  UIView+Shadow.swift
//  Multiplatform Redux Sample
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
