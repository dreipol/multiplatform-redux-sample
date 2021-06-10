//
//  NextDisposalCell.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit
import ReduxSampleShared
import dreiKit

class NextDisposalCell: IdentifiableTableViewCell {
    static let cellIdentifier = "NextDisposalCell"

    private let dateLabel = UILabel.label()
    private let typeLabel = UILabel.paragraph1()
    private let locationLabel = UILabel.paragraph1()
    private let disposalIcon = CircularDisposalImage(withSize: 62, iconSize: 48).autolayout()

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: UITableViewCell.CellStyle.subtitle, reuseIdentifier: reuseIdentifier)
        setupCell()
    }

    override var isHighlighted: Bool {
        didSet {
            alpha = isHighlighted ? kHighlightAlphaValue : 1
        }
    }

    func configureWith(model: DisposalCalendarEntry) {
        disposalIcon.setImage(name: model.disposal.disposalType.iconId)
        dateLabel.text = model.buildTimeString { (key) -> String in
            key.localized
        }
        typeLabel.text = model.disposal.disposalType.translationKey.localized
        locationLabel.text = String(format: model.locationReplaceable.localized, model.disposal.zip.description)
    }

    private func setupCell() {
        selectionStyle = .none
        backgroundColor = .primaryLight
        let cardView = UIView.autoLayout()
        contentView.addSubview(cardView)
        cardView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: 0, leading: 0, bottom: kUnit2, trailing: 0))
        cardView.backgroundColor = .primaryDark
        cardView.clipsToBounds = true
        cardView.layer.cornerRadius = kCardCornerRadius
        cardView.layer.addShadow(y: 10, blur: 10)

        addDisposalTypeIcon(cardView: cardView)

        let vStack = UIStackView.autoLayout()
        vStack.axis = .vertical
        vStack.isLayoutMarginsRelativeArrangement = true
        cardView.addSubview(vStack)
        vStack.leadingAnchor.constraint(equalTo: disposalIcon.trailingAnchor, constant: kUnit2).isActive = true
        vStack.topAnchor.constraint(equalTo: disposalIcon.topAnchor).isActive = true

        dateLabel.textColor = .secondarySecondary
        dateLabel.textAlignment = .left
        vStack.addArrangedSubview(dateLabel)
        vStack.addSpace(kUnitSmall)
        typeLabel.textColor = .white
        locationLabel.textColor = .white
        vStack.addArrangedSubview(typeLabel)
        vStack.addArrangedSubview(locationLabel)
    }

    private func addDisposalTypeIcon(cardView: UIView) {
        cardView.addSubview(disposalIcon)
        disposalIcon.topAnchor.constraint(equalTo: cardView.topAnchor, constant: kUnit3).isActive = true
        disposalIcon.bottomAnchor.constraint(equalTo: cardView.bottomAnchor, constant: -kUnit3).isActive = true
        disposalIcon.leadingAnchor.constraint(equalTo: cardView.leadingAnchor, constant: kUnit2).isActive = true
    }
}
