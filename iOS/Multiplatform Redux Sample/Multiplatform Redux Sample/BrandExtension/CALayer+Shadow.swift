//
//  CALayer+Shadow.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 15.01.21.
//

import UIKit

extension CALayer {
  func addShadow(
    color: UIColor = .primaryDark,
    alpha: Float = 0.15,
    x: CGFloat = 0,
    y: CGFloat = 6,
    blur: CGFloat = 6,
    spread: CGFloat = 0) {
        masksToBounds = false
        shadowColor = color.cgColor
        shadowOpacity = alpha
        shadowOffset = CGSize(width: x, height: y)
        shadowRadius = blur / 2.0
        if spread == 0 {
          shadowPath = nil
        } else {
          let dx = -spread
          let rect = bounds.insetBy(dx: dx, dy: dx)
          shadowPath = UIBezierPath(rect: rect).cgPath
        }
        shouldRasterize = true
        rasterizationScale = UIScreen.main.scale
  }
}
