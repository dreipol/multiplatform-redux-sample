//
//  HeaderView.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 17.12.20.
//

import UIKit
import ReduxSampleShared

class HeaderView: HighlightableControl {
    let titleLabel = UILabel.h3()
    private let image = UIImageView.autoLayout()

    init() {
        super.init(frame: .zero)
        isUserInteractionEnabled = true
        translatesAutoresizingMaskIntoConstraints = false
        image.image = UIImage(named: "ic_36_chevron_left")?.withTintColor(.accentAccent)
        image.isUserInteractionEnabled = false
        addSubview(image)

        addSubview(titleLabel)

        addTarget(self, action: #selector(didTabBack), for: .touchUpInside)

        NSLayoutConstraint.activate([
            image.heightAnchor.constraint(equalToConstant: kUnit5),
            image.widthAnchor.constraint(equalToConstant: kUnit5),
            image.leadingAnchor.constraint(equalTo: leadingAnchor, constant: -10),
            image.topAnchor.constraint(equalTo: topAnchor),
            image.bottomAnchor.constraint(equalTo: bottomAnchor),

            titleLabel.centerYAnchor.constraint(equalTo: centerYAnchor),
            titleLabel.leadingAnchor.constraint(equalTo: image.trailingAnchor)
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    @objc func didTabBack() {
        _ = dispatch(NavigationAction.back)
    }
}
