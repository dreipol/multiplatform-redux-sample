//
//  UIStackView+Tools.swift
//  dreiKit
//
//  Created by Simon Müller on 17.09.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import UIKit.UIStackView
import UIKit.NSLayoutConstraint

public extension UIStackView {

    static func autoLayout(axis: NSLayoutConstraint.Axis) -> Self {
        let stackView = autoLayout()
        stackView.axis = axis
        return stackView
    }

    func addSpace(_ space: CGFloat) {
        let view = UIView.autoLayout()
        view.isUserInteractionEnabled = false
        let anchor = axis == .horizontal ? view.widthAnchor : view.heightAnchor
        anchor.constraint(equalToConstant: space).isActive = true
        addArrangedSubview(view)
    }

    func removeArrangedView(_ view: UIView) {
        removeArrangedSubview(view)
        view.removeFromSuperview()
    }

    func removeAllArrangedSubviews() {
        for view in arrangedSubviews {
            removeArrangedView(view)
        }
    }
}
