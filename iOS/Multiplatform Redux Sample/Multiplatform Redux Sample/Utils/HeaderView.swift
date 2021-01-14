//
//  HeaderView.swift
//  Multiplatform Redux Sample
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
        image.image = UIImage(named: "ic_36_chevron_left")
        image.isUserInteractionEnabled = false
        addSubview(image)

        titleLabel.textColor = .testAppBlue
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
