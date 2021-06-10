//
//  PrimaryButton.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 02.11.20.
//

import dreiKit
import Foundation
import UIKit.UIControl

class PrimaryButton: UIControl {
    private static let height: CGFloat = kButtonHeight
    private var unhighlightBackgroundColor = UIColor.primaryPrimary
    internal let label = UILabel.button()

    var text: String? {
        get { label.text }
        set { label.text = newValue?.uppercased() }
    }

    override var isHighlighted: Bool {
        didSet {
            Animation.highlight({
                self.backgroundColor = self.isHighlighted ? UIColor.primaryPale : self.unhighlightBackgroundColor
            }, hightlight: self.isHighlighted)
        }
    }

    override var isEnabled: Bool {
        didSet {
            backgroundColor = isEnabled ? unhighlightBackgroundColor : .monochromesGreyLight
            label.textColor = isEnabled ? .primaryDark : .white
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        backgroundColor = unhighlightBackgroundColor
        layer.cornerRadius = kButtonCornerRadius
        clipsToBounds = true
        addSubview(label)
        NSLayoutConstraint.activate([
            heightAnchor.constraint(equalToConstant: Self.height),
            label.centerXAnchor.constraint(equalTo: centerXAnchor),
            label.centerYAnchor.constraint(equalTo: centerYAnchor)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
