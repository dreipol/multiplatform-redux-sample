//
//  HorizontalDoubleView.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 09.03.21.
//

import UIKit.UIView

class HorizontalDoublekView<T>: UIView where T: UIView {
    let leading = T.autoLayout()
    let trailing = T.autoLayout()

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(leading)
        addSubview(trailing)

        NSLayoutConstraint.activate([
            leading.leadingAnchor.constraint(equalTo: leadingAnchor),
            leading.topAnchor.constraint(equalTo: topAnchor),
            leading.bottomAnchor.constraint(equalTo: bottomAnchor),
            leading.trailingAnchor.constraint(equalTo: centerXAnchor),
            trailing.leadingAnchor.constraint(equalTo: centerXAnchor),
            trailing.topAnchor.constraint(equalTo: topAnchor),
            trailing.bottomAnchor.constraint(equalTo: leading.bottomAnchor),
            trailing.trailingAnchor.constraint(equalTo: trailingAnchor),
        ])
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
