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
    private let dateLabel = UILabel.label()
    private let typeLabel = UILabel.paragraph1()
    private let locationLabel = UILabel.paragraph1()
    private let roundDisposalIcon = RoundDisposalImage(withSize: 62, iconSize: 48)

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
        roundDisposalIcon.setImage(name: model.disposal.disposalType.iconId)
        dateLabel.text = model.buildTimeString { (key) -> String in
            key.localized
        }
        typeLabel.text = model.disposal.disposalType.translationKey.localized
        locationLabel.text = String(format: model.locationReplaceable.localized, model.disposal.zip.description)
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
        cardView.layer.addShadow(y: 10, blur: 10)

        addDisposalTypeIcon(cardView: cardView)

        let vStack = UIStackView.autoLayout()
        vStack.axis = .vertical
        vStack.isLayoutMarginsRelativeArrangement = true
        cardView.addSubview(vStack)
        vStack.leadingAnchor.constraint(equalTo: roundDisposalIcon.trailingAnchor, constant: kUnit2).isActive = true
        vStack.topAnchor.constraint(equalTo: roundDisposalIcon.topAnchor).isActive = true

        dateLabel.textColor = .testAppGreen
        dateLabel.textAlignment = .left
        vStack.addArrangedSubview(dateLabel)
        vStack.addSpace(kUnitSmall)
        typeLabel.textColor = .white
        locationLabel.textColor = .white
        vStack.addArrangedSubview(typeLabel)
        vStack.addArrangedSubview(locationLabel)
    }

    private func addDisposalTypeIcon(cardView: UIView) {
        cardView.addSubview(roundDisposalIcon)
        roundDisposalIcon.topAnchor.constraint(equalTo: cardView.topAnchor, constant: kUnit3).isActive = true
        roundDisposalIcon.bottomAnchor.constraint(equalTo: cardView.bottomAnchor, constant: -kUnit3).isActive = true
        roundDisposalIcon.leadingAnchor.constraint(equalTo: cardView.leadingAnchor, constant: kUnit2).isActive = true
    }
}
