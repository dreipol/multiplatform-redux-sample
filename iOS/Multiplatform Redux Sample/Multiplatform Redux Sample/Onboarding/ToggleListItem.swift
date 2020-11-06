//
//  ToggleListItem.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 06.11.20.
//

import UIKit

class ToggleListItem: UIControl {

    private let imageView: UIImageView = UIImageView.autoLayout()
    private let label = UILabel.h3()
    private let lineView = UIView.autoLayout()

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    init(text: String?, image: String?) {
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

        addImage(image, stackView)
        addLabel(text, stackView)
        addSwitch(stackView)
        addLineView()
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
        let toggleSwitch = UISwitch.autoLayout()
        toggleSwitch.isEnabled = true
        toggleSwitch.tintColor = UIColor.testAppGreenDark
        toggleSwitch.setContentHuggingPriority(.required, for: .horizontal)
        stackView.addArrangedSubview(toggleSwitch)
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
}
