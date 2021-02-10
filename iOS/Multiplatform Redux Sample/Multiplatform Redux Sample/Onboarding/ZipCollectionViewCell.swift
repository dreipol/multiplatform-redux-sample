//
//  ZipCollectionViewCell.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 05.11.20.
//

import UIKit

class ZipCollectionViewCell: UICollectionViewCell {

    var label = UILabel.h3()

    override init(frame: CGRect) {
        super.init(frame: .zero)

        contentView.backgroundColor = UIColor.testAppWhite
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
