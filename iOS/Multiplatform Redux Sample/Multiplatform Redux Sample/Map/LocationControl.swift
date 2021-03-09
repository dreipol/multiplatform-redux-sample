//
//  LocationControl.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 05.03.21.
//

import dreiKit
import UIKit

class LocationControl: UIControl {
    override var isHighlighted: Bool {
        didSet {
            Animation.highlight({
                self.alpha = self.isHighlighted ? 0.3 : 1.0
            }, hightlight: self.isHighlighted)
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        backgroundColor = .clear
        clipsToBounds = true
        let image = UIImageView.autoLayout(image: UIImage(named: "ic_location"))
        addSubview(image)
        NSLayoutConstraint.activate([
            image.centerXAnchor.constraint(equalTo: centerXAnchor),
            image.centerYAnchor.constraint(equalTo: centerYAnchor),
            heightAnchor.constraint(equalToConstant: kButtonHeight),
            widthAnchor.constraint(equalToConstant: kButtonHeight)
        ])
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
