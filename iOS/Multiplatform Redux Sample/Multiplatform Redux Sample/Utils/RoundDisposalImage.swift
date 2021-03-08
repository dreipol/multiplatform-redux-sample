//
//  RoundDisposalImage.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 15.01.21.
//

import UIKit

class RoundColoredImage: UIView {
    private let backgroundView = UIView.autoLayout()
    private let imageView = UIImageView.autoLayout()

    init(withSize: CGFloat, iconSize: CGFloat, backgroundColor: UIColor) {
        super.init(frame: .zero)
        translatesAutoresizingMaskIntoConstraints = false

        heightAnchor.constraint(equalToConstant: withSize).isActive = true
        backgroundView.makeRound(height: withSize, color: backgroundColor.cgColor)
        addSubview(backgroundView)
        backgroundView.fillSuperview()
        addSubview(imageView)
        imageView.widthAnchor.constraint(equalToConstant: iconSize).isActive = true
        imageView.heightAnchor.constraint(equalToConstant: iconSize).isActive = true
        imageView.centerXAnchor.constraint(equalTo: backgroundView.centerXAnchor).isActive = true
        imageView.centerYAnchor.constraint(equalTo: backgroundView.centerYAnchor).isActive = true
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func setImage(name: String) {
        imageView.image = UIImage(named: name)
    }
}

class RoundDisposalImage: RoundColoredImage {
    init(withSize: CGFloat, iconSize: CGFloat) {
        super.init(withSize: withSize, iconSize: iconSize, backgroundColor: .primaryLight)
    }
}
