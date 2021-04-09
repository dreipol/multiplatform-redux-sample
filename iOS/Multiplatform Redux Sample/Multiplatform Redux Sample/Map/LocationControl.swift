//
//  LocationControl.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 05.03.21.
//

import dreiKit
import UIKit
import MapKit

private let noneImage = UIImage(systemName: "location")
private let followImage = UIImage(systemName: "location.fill")
private let headingImage = UIImage(systemName: "location.north.line.fill")

class LocationControl: UIControl {
    private let iconView = UIImageView.autoLayout()

    var trackingType = MKUserTrackingMode.none {
        didSet {
            if trackingType != oldValue {
                updateForTrackingType()
            }
        }
    }

    override var isHighlighted: Bool {
        didSet {
            Animation.highlight({
                self.alpha = self.isHighlighted ? 0.5 : 1.0
            }, hightlight: self.isHighlighted)
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        clipsToBounds = true
        layer.cornerRadius = kButtonHeight / 2
        layer.masksToBounds = true
        addSubview(iconView)
        iconView.fillSuperviewMargins()
        iconView.contentMode = .scaleAspectFit
        NSLayoutConstraint.activate([
            heightAnchor.constraint(equalToConstant: kButtonHeight),
            widthAnchor.constraint(equalToConstant: kButtonHeight)
        ])
        updateForTrackingType()
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    private func updateForTrackingType() {
        switch trackingType {
        case .none:
            iconView.image = noneImage
            iconView.tintColor = .black
            backgroundColor = .white
        case .follow:
            iconView.image = followImage
            iconView.tintColor = .white
            backgroundColor = .primaryDark
        case .followWithHeading:
            iconView.image = headingImage
            iconView.tintColor = .white
            backgroundColor = .primaryDark
        @unknown default:
            break
        }
    }
}
