//
//  CollectionPointMapViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import ReduxSampleShared
import UIKit

class IconStack: UIView {
    let titleLabel = UILabel.paragraph2()
    let icons = UIStackView.autoLayout(axis: .horizontal)

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(titleLabel)
        addSubview(icons)
        icons.alignment = .center
        icons.distribution = .fill
        icons.spacing = kUnit1

        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: topAnchor),
            titleLabel.leadingAnchor.constraint(equalTo: leadingAnchor),
            titleLabel.trailingAnchor.constraint(equalTo: trailingAnchor),
            icons.leadingAnchor.constraint(equalTo: leadingAnchor),
            icons.trailingAnchor.constraint(lessThanOrEqualTo: trailingAnchor),
            icons.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: 8),
            icons.bottomAnchor.constraint(equalTo: bottomAnchor),
        ])
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func setIcons(from imageNames: [String]) {
        icons.removeAllArrangedSubviews()
        imageNames.compactMap { name in
            let imageView = RoundDisposalImage(withSize: kUnit4, iconSize: kUnit3)
            imageView.setImage(name: name)
            return imageView
        }.forEach { icon in
            icons.addArrangedSubview(icon)
        }
    }
}

class HorizontalDoublekView<T>: UIView where T: UIView {
    let leading = T.autoLayout()
    let trailing = T.autoLayout()

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(leading)
        addSubview(trailing)

        NSLayoutConstraint.activate([
            leading.leadingAnchor.constraint(equalTo: leadingAnchor),
            leading.topAnchor.constraint(equalTo: topAnchor),
            leading.bottomAnchor.constraint(equalTo: bottomAnchor),
            leading.trailingAnchor.constraint(equalTo: centerXAnchor),
            trailing.leadingAnchor.constraint(equalTo: centerXAnchor),
            trailing.topAnchor.constraint(equalTo: topAnchor),
            trailing.bottomAnchor.constraint(equalTo: bottomAnchor),
            trailing.trailingAnchor.constraint(equalTo: trailingAnchor),
        ])
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

extension UIButton {
    class func createLink() -> UIButton {
        let button = UIButton(type: .custom)
        button.translatesAutoresizingMaskIntoConstraints = false
        button.titleLabel?.font = .link()
        button.titleLabel?.textAlignment = .left
        button.titleLabel?.textColor = .accentDarkAccent
        return button
    }
}

class CollectionPointInfoView: UIView {
    let stack = UIStackView.autoLayout(axis: .vertical)
    let titleLabel = UILabel.h4()
    let iconStacks = HorizontalDoublekView<IconStack>.autoLayout()
    let addressLabel = UILabel.paragraph2()
    let mapLink = UIButton.createLink()

    override init(frame: CGRect) {
        super.init(frame: frame)
        backgroundColor = .white
        addSubview(stack)
        stack.fillSuperview(edgeInsets: .init(top: 0, leading: kUnit3, bottom: kUnit3, trailing: kUnit3))
        stack.spacing = kUnit2
        titleLabel.textAlignment = .left
        stack.addArrangedSubview(titleLabel)
        stack.addArrangedSubview(iconStacks)
        stack.addArrangedSubview(addressLabel)
        stack.addArrangedSubview(mapLink)
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(_ viewState: CollectionPointViewState) {
        kermit().d(viewState)
        titleLabel.text = viewState.title
        addressLabel.text = viewState.address
        mapLink.setTitle(viewState.navigationLink.text.localized, for: .normal)

        let typeStack = iconStacks.leading
        typeStack.titleLabel.text = viewState.collectionPointTypeTitle(localize: Localiser())
        typeStack.setIcons(from: viewState.collectionPointTypes.map { $0.icon })

        let wheelchairStack = iconStacks.trailing
        wheelchairStack.titleLabel.text = viewState.wheelChairAccessibleTitle.localized
        wheelchairStack.setIcons(from: [viewState.wheelChairAccessibleIcon])
//        TODO: make transparent when not accessible
    }
}

class Localiser: Localize {
    func localize(string: String) -> String {
        string.localized
    }
}
