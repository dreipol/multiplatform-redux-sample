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
    private let roundDisposalIcon = RoundDisposalImage(withSize: 36, iconSize: kUnit3)
    private let dateLabel = UILabel.h5()
    private let typeLabel = UILabel.paragraph2()
    private let notificationIcon = UIImageView.autoLayout()
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
        roundDisposalIcon.setImage(name: model.disposal.disposalType.iconId)
        dateLabel.text = model.buildTimeString { (key) -> String in
            key.localized
        }
        typeLabel.text = model.disposal.disposalType.translationKey.localized
        notificationIcon.image = UIImage(named: model.notificationIconId)
    }

    @objc func notificationTapped(tapGestureRecognizer: UITapGestureRecognizer) {
        _ = dispatch(ThunkAction(thunk: NotificationThunksKt.addOrRemoveNotificationThunk(disposalType: disposalType)))
    }

    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        if let touch = touches.first {
           if touch.view == notificationIcon {
                //began
                alpha = 0.5
           }
        }
        super.touchesBegan(touches, with: event)
    }

    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        if let touch = touches.first {
           if touch.view == notificationIcon {
                alpha = 1
           }
        }
        super.touchesEnded(touches, with: event)
    }

    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        if touches.count == 1 {
            //make sure when swiping over the boarder of the icon we cancel the touch down effect
            if let touch = touches.first, touch.location(in: notificationIcon).x < 0 || touch.location(in: notificationIcon).y < 0 {
                alpha = 1
            }
        }
    }

    override func touchesCancelled(_ touches: Set<UITouch>, with event: UIEvent?) {
        alpha = 1
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

        cardView.addSubview(roundDisposalIcon)
        roundDisposalIcon.topAnchor.constraint(equalTo: cardView.topAnchor, constant: kUnit1).isActive = true
        roundDisposalIcon.bottomAnchor.constraint(equalTo: cardView.bottomAnchor, constant: -kUnit1).isActive = true
        roundDisposalIcon.leadingAnchor.constraint(equalTo: cardView.leadingAnchor, constant: kUnit2).isActive = true

        cardView.addSubview(dateLabel)
        dateLabel.leadingAnchor.constraint(equalTo: roundDisposalIcon.trailingAnchor, constant: kUnit2).isActive = true
        dateLabel.centerYAnchor.constraint(equalTo: cardView.centerYAnchor).isActive = true

        cardView.addSubview(typeLabel)
        typeLabel.leadingAnchor.constraint(equalTo: dateLabel.trailingAnchor, constant: kUnit1).isActive = true
        typeLabel.centerYAnchor.constraint(equalTo: cardView.centerYAnchor).isActive = true

        cardView.addSubview(notificationIcon)
        notificationIcon.heightAnchor.constraint(equalToConstant: kUnit3).isActive = true
        notificationIcon.widthAnchor.constraint(equalToConstant: kUnit3).isActive = true
        notificationIcon.trailingAnchor.constraint(equalTo: cardView.trailingAnchor, constant: -kUnit2).isActive = true
        notificationIcon.centerYAnchor.constraint(equalTo: cardView.centerYAnchor).isActive = true
        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(notificationTapped(tapGestureRecognizer:)))
        tapGestureRecognizer.cancelsTouchesInView = false
        notificationIcon.isUserInteractionEnabled = true
        notificationIcon.addGestureRecognizer(tapGestureRecognizer)
    }
}
