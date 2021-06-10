//
//  ZipCollectionViewCell.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 05.11.20.
//

import UIKit

class ZipCollectionViewCell: UICollectionViewCell {

    var label = UILabel.h3()

    override init(frame: CGRect) {
        super.init(frame: .zero)

        contentView.backgroundColor = .white
        contentView.layer.cornerRadius = kButtonCornerRadius
        contentView.addSubview(label)
        label.textColor = .monochromesDarkGrey
        label.fitSuperview()
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override var isHighlighted: Bool {
        didSet {
            alpha = isHighlighted ? kHighlightAlphaValue : 1
        }
    }

}
