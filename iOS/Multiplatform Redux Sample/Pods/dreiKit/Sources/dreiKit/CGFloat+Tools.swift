//
//  CGFloat+Tools.swift
//  dreiKit
//
//  Created by Simon Müller on 09.09.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import UIKit

public extension CGFloat {
    static func random() -> CGFloat {
        return CGFloat(arc4random()) / CGFloat(UINT32_MAX)
    }

    static func radiansFrom(degree: CGFloat) -> CGFloat {
        return degree * CGFloat.pi/180
//        return CGFloat(Measurement(value: Double(degree), unit: UnitAngle.degrees).converted(to: .radians).value)
    }
}
