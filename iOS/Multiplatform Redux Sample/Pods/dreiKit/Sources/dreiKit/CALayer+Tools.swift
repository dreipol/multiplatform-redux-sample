//
//  CALayer+Tools.swift
//  dreiKit
//
//  Created by Simon Müller on 11.11.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import UIKit

public extension CALayer {
    func rotate(_ degree: CGFloat, combineTransform: Bool = false) {
        let angle = degree * CGFloat.pi / 180
        if combineTransform {
            transform = CATransform3DRotate(transform, angle, 0, 0, 1)
        } else {
            transform = CATransform3DMakeRotation(angle, 0, 0, 1)
        }
    }

    func setFrameTo(edge: UIRectEdge, view: UIView, thickness: CGFloat) {
        switch edge {
        case .top:
            frame = CGRect(x: 0, y: 0, width: view.frame.width, height: thickness)
        case .bottom:
            frame = CGRect(x: 0, y: view.frame.height - thickness, width: view.frame.width, height: thickness)
        case .left:
            frame = CGRect(x: 0, y: 0, width: thickness, height: view.frame.height)
        case .right:
            frame = CGRect(x: view.frame.width - thickness, y: 0, width: thickness, height: view.frame.height)
        default:
            break
        }
    }
}
