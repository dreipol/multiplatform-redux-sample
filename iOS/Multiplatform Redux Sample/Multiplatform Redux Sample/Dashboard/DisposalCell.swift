//
//  DisposalCell.swift
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

private class NotificationControl: UIControl {
    private let icon = UIImageView.autoLayout()

    override var isHighlighted: Bool {
        didSet {
            alpha = isHighlighted ? kHighlightAlphaValue : 1
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        self.addSubview(icon)
        NSLayoutConstraint.activate([
            icon.centerYAnchor.constraint(equalTo: centerYAnchor),
            icon.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -kUnit2/2),
            icon.heightAnchor.constraint(equalToConstant: kUnit3),
            icon.widthAnchor.constraint(equalToConstant: kUnit3),
        ])
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func setIcon(for model: DisposalCalendarEntry) {
        icon.image = UIImage(named: model.notificationIconId)
    }
}

class DisposalCell: IdentifiableTableViewCell {
    static let cellIdentifier = "DisposalCell"

    private let disposalIcon = CircularDisposalImage(withSize: 36, iconSize: kUnit3).autolayout()
    private let dateLabel = UILabel.h5()
    private let typeLabel = UILabel.paragraph2()
    private let notificationControl = NotificationControl.autoLayout()
    private var disposalType = DisposalType.bioWaste

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: UITableViewCell.CellStyle.subtitle, reuseIdentifier: reuseIdentifier)
        setupCell()
    }

    func configureWith(model: DisposalCalendarEntry) {
        disposalType = model.disposal.disposalType
        disposalIcon.setImage(name: model.disposal.disposalType.iconId)
        dateLabel.text = model.buildTimeString { (key) -> String in
            key.localized
        }
        typeLabel.text = model.disposal.disposalType.translationKey.localized
        notificationControl.setIcon(for: model)
    }

    @objc func notificationTapped(tapGestureRecognizer: UITapGestureRecognizer) {
        _ = dispatch(ThunkAction(thunk: NotificationThunksKt.addOrRemoveNotificationThunk(disposalType: disposalType)))
    }

    func setupCell() {
        selectionStyle = .none
        contentView.backgroundColor = .primaryLight
        let cardView = UIView.autoLayout()
        contentView.addSubview(cardView)
        cardView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: 0, leading: 0, bottom: kUnit1, trailing: 0))
        cardView.backgroundColor = .white
        cardView.clipsToBounds = true
        cardView.layer.cornerRadius = kCardCornerRadius
        cardView.layer.addShadow()

        cardView.addSubview(disposalIcon)
        cardView.addSubview(dateLabel)
        cardView.addSubview(typeLabel)
        cardView.addSubview(notificationControl)

        NSLayoutConstraint.activate([
            disposalIcon.topAnchor.constraint(equalTo: cardView.topAnchor, constant: kUnit1),
            disposalIcon.bottomAnchor.constraint(equalTo: cardView.bottomAnchor, constant: -kUnit1),
            disposalIcon.leadingAnchor.constraint(equalTo: cardView.leadingAnchor, constant: kUnit2),
            dateLabel.leadingAnchor.constraint(equalTo: disposalIcon.trailingAnchor, constant: kUnit2),
            dateLabel.centerYAnchor.constraint(equalTo: cardView.centerYAnchor),
            typeLabel.leadingAnchor.constraint(equalTo: dateLabel.trailingAnchor, constant: kUnit1),
            typeLabel.centerYAnchor.constraint(equalTo: cardView.centerYAnchor),
            notificationControl.topAnchor.constraint(equalTo: cardView.topAnchor),
            notificationControl.bottomAnchor.constraint(equalTo: cardView.bottomAnchor),
            notificationControl.widthAnchor.constraint(equalToConstant: kButtonHeight),
            notificationControl.trailingAnchor.constraint(equalTo: cardView.trailingAnchor, constant: -kUnit2/2),
        ])

        notificationControl.addTarget(self, action: #selector(notificationTapped(tapGestureRecognizer:)), for: .touchUpInside)
    }
}
