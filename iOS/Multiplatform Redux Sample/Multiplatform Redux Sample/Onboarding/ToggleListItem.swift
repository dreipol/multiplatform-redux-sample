//
//  ToggleListItem.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 06.11.20.
//

import UIKit
import ReduxSampleShared

class ToggleListItem: UIControl {

    private let imageView: UIImageView = UIImageView.autoLayout()
    private let label = UILabel.h3()
    private let toggleSwitch = UISwitch.autoLayout()
    private let lineView = UIView.autoLayout()
    let disposalType: DisposalType!

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    init(type: DisposalType) {
        disposalType = type
        super.init(frame: .zero)
        translatesAutoresizingMaskIntoConstraints = false
        isUserInteractionEnabled = true

        let stackView = UIStackView.autoLayout()
        stackView.distribution = .fill
        stackView.isUserInteractionEnabled = false
        stackView.axis = .horizontal
        stackView.alignment = .center
        stackView.spacing = 12
        addSubview(stackView)
        stackView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: kUnit2, leading: kUnit5, bottom: kUnit2, trailing: kUnit5))

        addImage(type.iconId, stackView)
        addLabel(type.translationKey, stackView)
        addSwitch(stackView)
        addLineView()
    }

    func setToggle(enabled: Bool) {
        toggleSwitch.setOn(enabled, animated: true)
    }

    fileprivate func addLineView() {
        lineView.backgroundColor = UIColor.testAppGreen
        addSubview(lineView)
        lineView.heightAnchor.constraint(equalToConstant: 1).isActive = true
        lineView.bottomAnchor.constraint(equalTo: bottomAnchor).isActive = true
        lineView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -kUnit5).isActive = true
        lineView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: kUnit5).isActive = true
    }

    fileprivate func addSwitch(_ stackView: UIStackView) {
        toggleSwitch.isEnabled = true
        toggleSwitch.isOn = true
        toggleSwitch.addTarget(self, action: #selector(switchValueDidChange(_:)), for: .valueChanged)
        toggleSwitch.tintColor = UIColor.testAppGreenDark
        toggleSwitch.setContentHuggingPriority(.required, for: .horizontal)
        stackView.addArrangedSubview(toggleSwitch)

        let tap = UITapGestureRecognizer(target: self, action: #selector(didTapInside))
        addGestureRecognizer(tap)
    }

    fileprivate func addLabel(_ text: String?, _ stackView: UIStackView) {
        label.text = text?.localized
        label.textAlignment = .left
        label.textColor = UIColor.white
        stackView.addArrangedSubview(label)
    }

    fileprivate func addImage(_ image: String?, _ stackView: UIStackView) {
        if let imageName = image {
            imageView.image = UIImage(named: imageName)
            imageView.setContentHuggingPriority(.required, for: .horizontal)
            stackView.addArrangedSubview(imageView)
        }
    }

    @objc func switchValueDidChange(_ sender: UISwitch) {
        print(sender)
    }

    @objc func didTapInside() {
        _ = dispatch(UpdateShowDisposalType(disposalType: disposalType, show: !toggleSwitch.isOn))
    }
}
