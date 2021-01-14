//
//  UIView+AutoLayout.swift
//  dreiKit
//
//  Created by Simon Müller on 10.09.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import UIKit

public extension UIView {

    class func autoLayout() -> Self {
        let view = self.init(frame: .zero)
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }

    func activateConstraints(
        visualFormat format: String,
        options opts: NSLayoutConstraint.FormatOptions = [],
        metrics: [String: Any]?,
        views: [String: Any]
    ) {
        NSLayoutConstraint.activate(NSLayoutConstraint.constraints(withVisualFormat: format, options: opts, metrics: metrics, views: views))
    }

    func fitSuperview() {
        guard let view = superview else {
            return
        }
        NSLayoutConstraint.activate([
            leadingAnchor.constraint(equalTo: view.leadingAnchor),
            widthAnchor.constraint(equalTo: view.widthAnchor),
            topAnchor.constraint(equalTo: view.topAnchor),
            heightAnchor.constraint(equalTo: view.heightAnchor),
            ])
    }

    func fitVerticalScrollView() {
        guard let view = superview else {
            return
        }
        NSLayoutConstraint.activate([
            leadingAnchor.constraint(equalTo: view.leadingAnchor),
            widthAnchor.constraint(equalTo: view.widthAnchor),
            topAnchor.constraint(equalTo: view.topAnchor),
            bottomAnchor.constraint(equalTo: view.bottomAnchor),
            ])
    }

    func fillSuperview(edgeInsets: NSDirectionalEdgeInsets = .zero) {
        guard let view = superview else {
            return
        }
        NSLayoutConstraint.activate([
            leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: edgeInsets.leading),
            trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -edgeInsets.trailing),
            topAnchor.constraint(equalTo: view.topAnchor, constant: edgeInsets.top),
            bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -edgeInsets.bottom),
            ])
    }

    func fillSuperviewMargins(edgeInsets: NSDirectionalEdgeInsets = .zero) {
        guard let view = superview else {
            return
        }
        NSLayoutConstraint.activate([
            leadingAnchor.constraint(equalTo: view.layoutMarginsGuide.leadingAnchor, constant: edgeInsets.leading),
            trailingAnchor.constraint(equalTo: view.layoutMarginsGuide.trailingAnchor, constant: -edgeInsets.trailing),
            topAnchor.constraint(equalTo: view.layoutMarginsGuide.topAnchor, constant: edgeInsets.top),
            bottomAnchor.constraint(equalTo: view.layoutMarginsGuide.bottomAnchor, constant: -edgeInsets.bottom),
            ])
    }

    func fillWithLowTrailingPriority() {
        guard let view = superview else {
            return
        }
        let trailing = trailingAnchor.constraint(equalTo: view.trailingAnchor)
        trailing.priority = .defaultLow

        NSLayoutConstraint.activate([
            trailing,
            leadingAnchor.constraint(equalTo: view.leadingAnchor),
            topAnchor.constraint(equalTo: view.topAnchor),
            bottomAnchor.constraint(equalTo: view.bottomAnchor),
        ])
    }

    func fillWithLowBottomPriority() {
        guard let view = superview else {
            return
        }
        let bottom = bottomAnchor.constraint(equalTo: view.bottomAnchor)
        bottom.priority = .defaultLow

        NSLayoutConstraint.activate([
            bottom,
            topAnchor.constraint(equalTo: view.topAnchor),
            leadingAnchor.constraint(equalTo: view.leadingAnchor),
            trailingAnchor.constraint(equalTo: view.trailingAnchor),
        ])
    }

    var layoutHeight: CGFloat {
        return systemLayoutSizeFitting(UIView.layoutFittingCompressedSize).height
    }

}

public protocol ViewInit: class {
    associatedtype InitData

    func configure(with initData: InitData)
}

public extension ViewInit where Self: UIView {
    static func autoLayout(with initData: InitData) -> Self {
        let view = autoLayout()
        view.configure(with: initData)
        return view
    }
}
