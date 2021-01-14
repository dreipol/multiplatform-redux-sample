//
//  CALayer+Animation.swift
//  dreiKit
//
//  Created by Simon Müller on 07.11.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import UIKit
import QuartzCore

let kSpringRotationAnimationKey = "kSpringRotationAnimationKey"
let kOpacityAnimationKey = "kOpacityAnimationKey"

public extension CALayer {

    func springAnimate(degree: CGFloat, repeatCount: Float = 1, delegate: CAAnimationDelegate? = nil) {
        let animation = CASpringAnimation(keyPath: "transform.rotation")
        animation.toValue = CGFloat.radiansFrom(degree: degree)
        animation.beginTime = CACurrentMediaTime()
        animation.damping = 1000
        animation.initialVelocity = 0
        animation.mass = 1
        animation.stiffness = 20
        animation.delegate = delegate
        animation.duration = animation.settlingDuration
        animation.repeatCount = repeatCount
        add(animation, forKey: kSpringRotationAnimationKey)
    }

    // Still has some problem around
    func animate(opacity: Float, duration: CFTimeInterval, delegate: CAAnimationDelegate? = nil) {
        let animation = CABasicAnimation(keyPath: "opacity")
        animation.fromValue = presentation()?.opacity
        animation.toValue = opacity
        animation.beginTime = CACurrentMediaTime()
        animation.duration = duration
        animation.timingFunction = CAMediaTimingFunction(name: CAMediaTimingFunctionName.easeOut)
        animation.delegate = delegate
        animation.fillMode = .forwards
        self.opacity = opacity
        add(animation, forKey: kOpacityAnimationKey)
    }

}
