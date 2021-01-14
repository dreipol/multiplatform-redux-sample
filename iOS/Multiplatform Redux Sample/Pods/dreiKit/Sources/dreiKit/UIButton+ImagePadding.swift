//
//  UIButton+ImagePadding.swift
//  dreiKit
//
//  Created by Nils Becker on 08.09.20.
//

import Foundation
import UIKit.UIButton

public extension UIButton {
    func setImagePadding(_ padding: CGFloat) {
        let padding = semanticContentAttribute == .forceRightToLeft ? -padding : padding
        // The default padding is 8, 16, 8, 16, so we add to that
        contentEdgeInsets = UIEdgeInsets(top: 8, left: 16, bottom: 8, right: 16 + padding)
        titleEdgeInsets = UIEdgeInsets(top: 0, left: padding, bottom: 0, right: -padding)
    }
}
