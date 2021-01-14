//
//  SettingsEntryControl.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 14.12.20.
//

import UIKit
import ReduxSampleShared

class SettingsEntryControl: HighlightableControl {

    private let titleLabel = UILabel.h3()
    private let image = UIImageView.autoLayout()
    private let navigationAction: NavigationAction?

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    init(model: SettingsEntry, isLast: Bool) {
        navigationAction = model.navigationAction
        super.init(frame: .zero)
        setupCell(isLast)
        titleLabel.text = model.descriptionKey.localized
        let tap = UITapGestureRecognizer(target: self, action: #selector(didTapInside))
        addGestureRecognizer(tap)
    }

    func setupCell(_ isLast: Bool) {
        let cardView = UIView.autoLayout()
        addSubview(cardView)
        cardView.fillSuperview()
        cardView.heightAnchor.constraint(equalToConstant: kUnit9).isActive = true
        cardView.clipsToBounds = true
        cardView.isUserInteractionEnabled = false

        titleLabel.textAlignment = .left
        titleLabel.textColor = .testAppBlue

        cardView.addSubview(titleLabel)
        titleLabel.leadingAnchor.constraint(equalTo: cardView.leadingAnchor, constant: kUnit2).isActive = true
        titleLabel.centerYAnchor.constraint(equalTo: cardView.centerYAnchor).isActive = true

        image.image = UIImage(named: "ic_36_chevron_right")
        cardView.addSubview(image)
        image.trailingAnchor.constraint(equalTo: cardView.trailingAnchor, constant: -kUnit3).isActive = true
        image.centerYAnchor.constraint(equalTo: cardView.centerYAnchor).isActive = true

        if !isLast {
            let line = UIView.autoLayout()
            line.backgroundColor = UIColor.testAppGreen
            cardView.addSubview(line)
            line.heightAnchor.constraint(equalToConstant: 1).isActive = true
            line.leadingAnchor.constraint(equalTo: cardView.leadingAnchor, constant: kUnit2).isActive = true
            line.trailingAnchor.constraint(equalTo: cardView.trailingAnchor, constant: -kUnit2).isActive = true
            line.bottomAnchor.constraint(equalTo: cardView.bottomAnchor).isActive = true
        }
    }

    @objc func didTapInside() {
        if let action = navigationAction {
            _ = dispatch(action)
        }
    }

}
