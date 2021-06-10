//
//  CircularColoredImage.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 15.01.21.
//

import UIKit

class CircularColoredImage: UIView {
    private let backgroundView = UIView.autoLayout()
    private let imageView = UIImageView.autoLayout()

    init(withSize size: CGFloat, iconSize: CGFloat, backgroundColor: UIColor) {
        super.init(frame: .zero)

        backgroundView.makeCircular(radius: size / 2, color: backgroundColor.cgColor)
        addSubview(backgroundView)
        addSubview(imageView)

        backgroundView.fillSuperview()
        NSLayoutConstraint.activate([
            heightAnchor.constraint(equalToConstant: size),
            imageView.widthAnchor.constraint(equalToConstant: iconSize),
            imageView.heightAnchor.constraint(equalToConstant: iconSize),
            imageView.centerXAnchor.constraint(equalTo: backgroundView.centerXAnchor),
            imageView.centerYAnchor.constraint(equalTo: backgroundView.centerYAnchor)
        ])
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func setImage(name: String) {
        imageView.image = UIImage(named: name)
    }
}

class CircularDisposalImage: CircularColoredImage {
    init(withSize: CGFloat, iconSize: CGFloat) {
        super.init(withSize: withSize, iconSize: iconSize, backgroundColor: .primaryLight)
    }
}
