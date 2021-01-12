//
//  HeaderView.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 17.12.20.
//

import UIKit
import ReduxSampleShared

class HeaderView: UIView {
    let titleLabel = UILabel.h3()
    private let image = UIImageView.autoLayout()

    init() {
        super.init(frame: .zero)
        isUserInteractionEnabled = true
        translatesAutoresizingMaskIntoConstraints = false
        image.image = UIImage(named: "ic_36_chevron_left")
        image.heightAnchor.constraint(equalToConstant: kUnit5).isActive = true
        image.widthAnchor.constraint(equalToConstant: kUnit5).isActive = true
        image.isUserInteractionEnabled = true
        let tap = UITapGestureRecognizer(target: self, action: #selector(didTabBack))
        image.addGestureRecognizer(tap)
        addSubview(image)
        image.leadingAnchor.constraint(equalTo: leadingAnchor).isActive = true
        image.topAnchor.constraint(equalTo: topAnchor).isActive = true
        image.bottomAnchor.constraint(equalTo: bottomAnchor).isActive = true

        titleLabel.textColor = .testAppBlue
        addSubview(titleLabel)
        titleLabel.centerYAnchor.constraint(equalTo: centerYAnchor).isActive = true
        titleLabel.leadingAnchor.constraint(equalTo: image.trailingAnchor).isActive = true
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    @objc func didTabBack() {
        _ = dispatch(NavigationAction.back)
    }
}
