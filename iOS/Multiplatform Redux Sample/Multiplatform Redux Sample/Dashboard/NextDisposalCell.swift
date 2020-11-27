//
//  NextDisposalCell.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit
import ReduxSampleShared

class NextDisposalCell: UITableViewCell {

    static let reuseIdentifier = "NextDisposalCell"
    private let disposalIcon = UIImageView.autoLayout()
    private let dateLabel = UILabel.label()
    private let typeLabel = UILabel.paragraph1()
    private let locationLabel = UILabel.paragraph1()

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
        //TODO clarify why it has different formatting
        locationLabel.text = model.locationReplaceable.localized.replacingOccurrences(of: "%@", with: model.disposal.zip.description)
    }

    private func setupCell() {
        selectionStyle = .none
        backgroundColor = .testAppGreenLight
        let cardView = UIView.autoLayout()
        contentView.addSubview(cardView)
        cardView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: 0, leading: 0, bottom: kUnit2, trailing: 0))
        cardView.backgroundColor = .testAppBlue
        cardView.clipsToBounds = true
        cardView.layer.cornerRadius = kCardCornerRadius

        let disposalBackground = addDisposalTypeIcon(cardView: cardView)

        let vStack = UIStackView.autoLayout()
        vStack.axis = .vertical
        vStack.isLayoutMarginsRelativeArrangement = true
        cardView.addSubview(vStack)
        vStack.leadingAnchor.constraint(equalTo: disposalBackground.trailingAnchor, constant: kUnit2).isActive = true
        vStack.topAnchor.constraint(equalTo: disposalBackground.topAnchor).isActive = true

        dateLabel.textColor = .testAppGreen
        dateLabel.textAlignment = .left
        vStack.addArrangedSubview(dateLabel)
        vStack.addSpace(kUnitSmall)
        typeLabel.textColor = .white
        locationLabel.textColor = .white
        vStack.addArrangedSubview(typeLabel)
        vStack.addArrangedSubview(locationLabel)
    }

    private func addDisposalTypeIcon(cardView: UIView) -> UIView {
        let disposalBackground = UIImageView.autoLayout()
        cardView.addSubview(disposalBackground)
        disposalBackground.makeRound(height: 62)
        disposalBackground.topAnchor.constraint(equalTo: cardView.topAnchor, constant: kUnit3).isActive = true
        disposalBackground.bottomAnchor.constraint(equalTo: cardView.bottomAnchor, constant: -kUnit3).isActive = true
        disposalBackground.leadingAnchor.constraint(equalTo: cardView.leadingAnchor, constant: kUnit2).isActive = true

        cardView.addSubview(disposalIcon)
        disposalIcon.widthAnchor.constraint(equalToConstant: 44).isActive = true
        disposalIcon.heightAnchor.constraint(equalToConstant: 44).isActive = true
        disposalIcon.centerXAnchor.constraint(equalTo: disposalBackground.centerXAnchor).isActive = true
        disposalIcon.centerYAnchor.constraint(equalTo: disposalBackground.centerYAnchor).isActive = true
        return disposalBackground
    }
}
