//
//  PrimaryButton.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 02.11.20.
//

import dreiKit
import Foundation
import UIKit.UIControl

class PrimaryButton: UIControl {
    private static let height: CGFloat = kButtonHeight
    private var unhighlightBackgroundColor = UIColor.tetstAppGruen
    internal let label = UILabel.button()

    var text: String? {
        get { label.text }
        set { label.text = newValue?.uppercased() }
    }

    override var isHighlighted: Bool {
        didSet {
            Animation.highlight({
                self.backgroundColor = self.isHighlighted ? UIColor.testAppGreenDark : self.unhighlightBackgroundColor
            }, hightlight: self.isHighlighted)
        }
    }

    override var isEnabled: Bool {
        didSet {
            self.backgroundColor = isEnabled ? self.unhighlightBackgroundColor : UIColor.testAppBlackLight
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
