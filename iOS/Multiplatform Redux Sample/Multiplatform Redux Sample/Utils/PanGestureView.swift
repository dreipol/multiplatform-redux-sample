//
//  PanableGestureView.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 09.03.21.
//

import UIKit

private let minimalCloseVelocity: CGFloat = 1500
private let minimalCloseHeightFactor: CGFloat = 0.3
private let animationDuration: TimeInterval = 0.3

protocol PanGestureViewDelegate: class {
    func hide(view: PanGestureView)
}

class PanGestureView: UIView {
    private var observation: NSKeyValueObservation?

    @objc var activeBottomConstraint: NSLayoutConstraint? {
        didSet {
            if let constraint = activeBottomConstraint {
                startBottomConstant = constraint.constant
                observeActive()
            } else {
                observation = nil
            }
        }
    }

    private var startBottomConstant: CGFloat = 0

    weak var gestureDelegate: PanGestureViewDelegate?

    private func observeActive() {
        observation = activeBottomConstraint?.observe(\NSLayoutConstraint.isActive, options: [.new, .old]) { [weak self] const, change in
            if let isActive = change.newValue, isActive, isActive != change.oldValue, let self = self {
                const.constant = self.startBottomConstant
            }
        }
    }

    deinit {
        observation = nil
    }

    @objc
    func panGestureRecognizer(sender: UIPanGestureRecognizer) {
        let trans = sender.translation(in: self).y
        let velocity = sender.velocity(in: window).y

        switch sender.state {
        case .began:
            break
        case .changed:
            updateBottomConstraint(constant: max(trans, -kUnit3))
        case .ended, .cancelled:
            if trans > (frame.size.height * minimalCloseHeightFactor) || velocity > minimalCloseVelocity {
                gestureDelegate?.hide(view: self)
            } else {
                updateBottomConstraint(constant: startBottomConstant)
                UIView.animate(withDuration: animationDuration, animations: {
                    self.layoutIfNeeded()
                })
            }
        case .failed, .possible:
            updateBottomConstraint(constant: startBottomConstant)
        @unknown default:
            break
        }
    }

    private func updateBottomConstraint(constant: CGFloat) {
        activeBottomConstraint?.constant = constant
        setNeedsUpdateConstraints()
    }

    func addRecognizer() {
        let gestureRecognizer = UIPanGestureRecognizer(target: self, action: #selector(panGestureRecognizer))
        addGestureRecognizer(gestureRecognizer)
    }
}
