//
//  Animation.swift
//  dreiKit
//
//  Created by Samuel Bichsel on 21.07.20.
//

import Foundation
import UIKit.UIView

public struct Animation {
    public static func spring(_ animation: @escaping (() -> Void),
                              duration: TimeInterval = 0.3,
                              options: UIView.AnimationOptions = [.allowUserInteraction, .beginFromCurrentState],
                              completion: (() -> Void)? = nil) {
        UIView.animate(withDuration: duration,
                       delay: 0,
                       usingSpringWithDamping: 1,
                       initialSpringVelocity: 0,
                       options: options,
                       animations: {
                        animation()
        }, completion: { _ in
            completion?()
        })
    }

    public static func appearance(_ animation: @escaping (() -> Void)) {
        UIView.animate(withDuration: 0.3,
                       delay: 0,
                       usingSpringWithDamping: 1,
                       initialSpringVelocity: 0,
                       options: [.allowUserInteraction, .beginFromCurrentState],
                       animations: {
                        animation()
        }, completion: nil)
    }

    public static func highlight(_ animation: @escaping (() -> Void), hightlight: Bool, completion: (() -> Void)? = nil) {
        UIView.animate(withDuration: hightlight ? 0 : 0.3,
                       delay: 0,
                       usingSpringWithDamping: 1,
                       initialSpringVelocity: 0,
                       options: [.allowUserInteraction, .beginFromCurrentState],
                       animations: {
                        animation()
        }, completion: { _ in
            completion?()
        })
    }
}
