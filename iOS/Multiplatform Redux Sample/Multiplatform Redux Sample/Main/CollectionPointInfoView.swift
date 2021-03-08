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
    var iconBackgroundColor = UIColor.clear

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
            let imageView = RoundColoredImage(withSize: kUnit4, iconSize: kUnit3, backgroundColor: iconBackgroundColor)
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
        button.contentHorizontalAlignment = .left
        button.setTitleColor(.accentDarkAccent, for: .normal)
        button.setTitleColor(.accentAccent, for: .highlighted)

        return button
    }
}

class CollectionPointInfoView: UIView {
    let stack = UIStackView.autoLayout(axis: .vertical)
    let titleLabel = UILabel.h4()
    let iconStacks = HorizontalDoublekView<IconStack>.autoLayout()
    let addressLabel = UILabel.paragraph2()
    let mapLink = UIButton.createLink()
    let closeControl = UIButton(type: .custom)

    var startY: CGFloat = 0

    override init(frame: CGRect) {
        super.init(frame: frame)
        backgroundColor = .white
        addSubview(stack)
        stack.fillSuperview(edgeInsets: .init(top: kUnit1, leading: kUnit3, bottom: kUnit3, trailing: kUnit3))
        stack.spacing = kUnit2
        titleLabel.textAlignment = .left
        stack.addArrangedSubview(closeControl)
        stack.addArrangedSubview(titleLabel)
        stack.addArrangedSubview(iconStacks)
        stack.addArrangedSubview(addressLabel)
        stack.addArrangedSubview(mapLink)
        closeControl.tintColor = .accentAccent
        closeControl.setImage(UIImage(named: "ic_24_chevron_down"), for: .normal)
        addShadowAndCornerRadius()
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
        typeStack.iconBackgroundColor = .primaryLight
        typeStack.titleLabel.text = viewState.collectionPointTypeTitle(localize: Localiser())
        typeStack.setIcons(from: viewState.collectionPointTypes.map { $0.icon })

        let wheelchairStack = iconStacks.trailing
        wheelchairStack.iconBackgroundColor = .primaryDark
        wheelchairStack.titleLabel.text = viewState.wheelChairAccessibleTitle.localized
        wheelchairStack.setIcons(from: [viewState.wheelChairAccessibleIcon])
        wheelchairStack.alpha = viewState.wheelChairAccessible ? 1 : 0.3
    }

    private func addShadowAndCornerRadius() {
       layer.cornerRadius = 8
        layer.shadowColor = UIColor(red: 0/255, green: 112/255, blue: 188/255, alpha: 0.15000000596046448).cgColor
       layer.shadowOpacity = 1
       layer.shadowOffset = CGSize(width: 0, height: 6)
       layer.shadowRadius = 6 / 2
       layer.shadowPath = nil
    }
}

class Localiser: Localize {
    func localize(string: String) -> String {
        string.localized
    }
}
