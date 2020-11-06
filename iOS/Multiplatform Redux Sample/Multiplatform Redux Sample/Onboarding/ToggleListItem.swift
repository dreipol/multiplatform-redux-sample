//
//  ToggleListItem.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 06.11.20.
//

import UIKit

class ToggleListItem: UIControl {

    let imageView: UIImageView = UIImageView.autoLayout()
    let label = UILabel.h3()

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

        if let imageName = image {
            imageView.image = UIImage(named: imageName)
            imageView.setContentHuggingPriority(.required, for: .horizontal)
            stackView.addArrangedSubview(imageView)
        }

        label.text = text?.localized
        label.textAlignment = .left
        stackView.addArrangedSubview(label)

        let toggleSwitch = UISwitch.autoLayout()
        toggleSwitch.isEnabled = true
        toggleSwitch.tintColor = UIColor.testAppGreenDark
        toggleSwitch.setContentHuggingPriority(.required, for: .horizontal)
        stackView.addArrangedSubview(toggleSwitch)

    }

}
