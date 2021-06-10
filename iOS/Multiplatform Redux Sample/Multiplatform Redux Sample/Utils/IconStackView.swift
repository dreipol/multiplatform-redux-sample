//
//  IconStack.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Samuel Bichsel on 09.03.21.
//

import UIKit

class IconStackView: UIView {
    let titleLabel = UILabel.paragraph2()
    let icons = UIStackView.autoLayout(axis: .horizontal)
    var iconBackgroundColor = UIColor.clear

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(titleLabel)
        addSubview(icons)
        icons.alignment = .center
        icons.distribution = .fill
        icons.spacing = kUnit1
        titleLabel.numberOfLines = 1
        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: topAnchor),
            titleLabel.leadingAnchor.constraint(equalTo: leadingAnchor),
            titleLabel.trailingAnchor.constraint(equalTo: trailingAnchor),
            icons.leadingAnchor.constraint(equalTo: leadingAnchor),
            icons.trailingAnchor.constraint(lessThanOrEqualTo: trailingAnchor),
            icons.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 8),
            icons.bottomAnchor.constraint(equalTo: bottomAnchor),
        ])
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func setIcons(from imageNames: [String]) {
        icons.removeAllArrangedSubviews()
        imageNames.compactMap { name in
            let imageView = CircularColoredImage(withSize: kUnit4, iconSize: kUnit3, backgroundColor: iconBackgroundColor)
            imageView.autolayout().setImage(name: name)
            return imageView
        }.forEach { icon in
            icons.addArrangedSubview(icon)
        }
    }
}
