//
//  ZipCollectionViewCell.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 05.11.20.
//

import UIKit

class ZipCollectionViewCell: UICollectionViewCell {

    var label = UILabel.h3()
    var line = UIView.autoLayout()

    override init(frame: CGRect) {
        super.init(frame: .zero)

        contentView.backgroundColor = UIColor.testAppWhite
        contentView.layer.cornerRadius = kButtonCornerRadius
        contentView.addSubview(label)
        label.fitSuperview()

        line.backgroundColor = UIColor.testAppBlueLight
        contentView.addSubview(line)
        line.widthAnchor.constraint(equalToConstant: 164).isActive = true
        line.heightAnchor.constraint(equalToConstant: 1).isActive = true
        line.bottomAnchor.constraint(equalTo: bottomAnchor).isActive = true
        line.centerXAnchor.constraint(equalTo: centerXAnchor).isActive = true
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

}
