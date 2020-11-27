//
//  DisposalCell.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit
import ReduxSampleShared

class DisposalCell: UITableViewCell {

    static let reuseIdentifier = "DisposalCell"
    private let disposalIcon = UIImageView.autoLayout()
    private let dateLabel = UILabel.h5()
    private let typeLabel = UILabel.paragraph2()
    private let notificationIcon = UIImageView.autoLayout()

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: UITableViewCell.CellStyle.subtitle, reuseIdentifier: reuseIdentifier)
        setupCell()
    }

    func configureWith(model: DisposalNotification) {
        disposalIcon.image = UIImage(named: model.disposal.disposalType.iconId)
        dateLabel.text = model.buildTimeString { (key) -> String in
            key.localized
        }
        typeLabel.text = model.disposal.disposalType.translationKey.localized
        //TODO rename icons for notifications
        notificationIcon.image = UIImage(named: model.notificationIconId)
    }

    func setupCell() {
        selectionStyle = .none
        contentView.backgroundColor = .testAppGreenLight
        let cardView = UIView.autoLayout()
        contentView.addSubview(cardView)
        cardView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: 0, leading: 0, bottom: kUnit1, trailing: 0))
        cardView.backgroundColor = .white
        cardView.clipsToBounds = true
        cardView.layer.cornerRadius = kCardCornerRadius

        let disposalBackground = UIImageView.autoLayout()
        cardView.addSubview(disposalBackground)
        disposalBackground.makeRound(height: kUnit6)
        disposalBackground.topAnchor.constraint(equalTo: cardView.topAnchor, constant: kUnit1).isActive = true
        disposalBackground.bottomAnchor.constraint(equalTo: cardView.bottomAnchor, constant: -kUnit1).isActive = true
        disposalBackground.leadingAnchor.constraint(equalTo: cardView.leadingAnchor, constant: kUnit2).isActive = true

        cardView.addSubview(disposalIcon)
        disposalIcon.widthAnchor.constraint(equalToConstant: kUnit3).isActive = true
        disposalIcon.heightAnchor.constraint(equalToConstant: kUnit3).isActive = true
        disposalIcon.centerXAnchor.constraint(equalTo: disposalBackground.centerXAnchor).isActive = true
        disposalIcon.centerYAnchor.constraint(equalTo: disposalBackground.centerYAnchor).isActive = true

        cardView.addSubview(dateLabel)
        dateLabel.leadingAnchor.constraint(equalTo: disposalBackground.trailingAnchor, constant: kUnit2).isActive = true
        dateLabel.centerYAnchor.constraint(equalTo: cardView.centerYAnchor).isActive = true

        cardView.addSubview(typeLabel)
        typeLabel.leadingAnchor.constraint(equalTo: dateLabel.trailingAnchor, constant: kUnit1).isActive = true
        typeLabel.centerYAnchor.constraint(equalTo: cardView.centerYAnchor).isActive = true

        cardView.addSubview(notificationIcon)
        notificationIcon.heightAnchor.constraint(equalToConstant: kUnit3).isActive = true
        notificationIcon.widthAnchor.constraint(equalToConstant: kUnit3).isActive = true
        notificationIcon.trailingAnchor.constraint(equalTo: cardView.trailingAnchor, constant: -kUnit2).isActive = true
        notificationIcon.centerYAnchor.constraint(equalTo: cardView.centerYAnchor).isActive = true

    }
}
