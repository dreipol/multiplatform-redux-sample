//
//  UIView+AutoLayout.swift
//  dreiKit
//
//  Created by Simon Müller on 10.09.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import UIKit

public struct FillConstraints {
    public var leading: NSLayoutConstraint
    public var trailing: NSLayoutConstraint
    public var top: NSLayoutConstraint
    public var bottom: NSLayoutConstraint

    public var all: [NSLayoutConstraint] { [leading, trailing, top, bottom] }
}

public extension UIView {
    class func autoLayout() -> Self {
        return self.init(frame: .zero).autolayout()
    }

    func autolayout() -> Self {
        translatesAutoresizingMaskIntoConstraints = false
        return self
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

    func createFillSuperview(edgeInsets: NSDirectionalEdgeInsets = .zero) -> FillConstraints {
        guard let view = superview else {
            fatalError("superview is not set")
        }
        return FillConstraints(
            leading: leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: edgeInsets.leading),
            trailing: trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -edgeInsets.trailing),
            top: topAnchor.constraint(equalTo: view.topAnchor, constant: edgeInsets.top),
            bottom: bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -edgeInsets.bottom)
        )
    }

    func fillSuperview(edgeInsets: NSDirectionalEdgeInsets = .zero) {
        NSLayoutConstraint.activate(createFillSuperview(edgeInsets: edgeInsets))
    }

    func createFillSuperviewMargins(edgeInsets: NSDirectionalEdgeInsets = .zero) -> FillConstraints {
        guard let view = superview else {
            fatalError("superview is not set")
        }

        return FillConstraints(
            leading: leadingAnchor.constraint(equalTo: view.layoutMarginsGuide.leadingAnchor, constant: edgeInsets.leading),
            trailing: trailingAnchor.constraint(equalTo: view.layoutMarginsGuide.trailingAnchor, constant: -edgeInsets.trailing),
            top: topAnchor.constraint(equalTo: view.layoutMarginsGuide.topAnchor, constant: edgeInsets.top),
            bottom: bottomAnchor.constraint(equalTo: view.layoutMarginsGuide.bottomAnchor, constant: -edgeInsets.bottom)
        )
    }

    func fillSuperviewMargins(edgeInsets: NSDirectionalEdgeInsets = .zero) {
        NSLayoutConstraint.activate(createFillSuperviewMargins(edgeInsets: edgeInsets))
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

public extension NSLayoutConstraint {
    static func activate(_ constraints: FillConstraints) {
        activate(constraints.all)
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
