//
//  CollectionPointMapViewController.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Samuel Bichsel on 30.10.20.
//

import ReduxSampleShared
import UIKit
import dreiKit

protocol CollectionPointInfoViewDelegate: PanGestureViewDelegate {
    func didTapMapLink(view: CollectionPointInfoView, address: AddressString)
}

class CollectionPointInfoView: PanGestureView {
    let stack = UIStackView.autoLayout(axis: .vertical)

    let closeControl = UIButton(type: .custom).autolayout()
    let titleLabel = UILabel.h4()
    let iconStacks = UIStackView.autoLayout(axis: .horizontal)
    let addressLabel = UILabel.paragraph2()
    let mapLink = UIButton.createLink()
    var mapLinkAddress = AddressString("")
    weak var delegate: CollectionPointInfoViewDelegate?

    override var gestureDelegate: PanGestureViewDelegate? {
        get { delegate }
        set {
            if newValue == nil {
                delegate = nil
            } else if let d = newValue as? CollectionPointInfoViewDelegate {
                delegate = d
            } else {
                fatalError("Please use delegate instead of gestureDelegate")
            }
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        backgroundColor = .white
        addSubview(stack)

        let constraints = stack.createFillSuperview(edgeInsets: .init(top: kUnit1, leading: kUnit3, bottom: kUnit3, trailing: kUnit3))
        constraints.bottom.priority = UILayoutPriority(rawValue: 999)
        NSLayoutConstraint.activate(constraints)

        stack.spacing = kUnit2

        setupCloseControl()

        titleLabel.textAlignment = .left
        stack.addArrangedSubview(titleLabel)
        iconStacks.distribution = .fillEqually
        stack.addArrangedSubview(iconStacks)
        stack.addArrangedSubview(addressLabel)

        mapLink.addTarget(self, action: #selector(didTapMapLink), for: .touchUpInside)
        stack.addArrangedSubview(mapLink)

        layer.cornerRadius = kButtonCornerRadius
        layer.addShadow(color: .black, alpha: 0.25)
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    @objc
    private func callDelegate() {
        delegate?.hide(view: self)
    }

    @objc
    private func didTapMapLink() {
        delegate?.didTapMapLink(view: self, address: mapLinkAddress)
    }

    func render(_ viewState: CollectionPointViewState) {
        titleLabel.text = viewState.title
        addressLabel.text = viewState.address
        mapLink.setTitle(viewState.navigationLink.text.localized, for: .normal)
        mapLinkAddress = AddressString(viewState.navigationLink.payload)
        iconStacks.removeAllArrangedSubviews()

        let typeStack = IconStackView.autoLayout()
        typeStack.iconBackgroundColor = .primaryLight
        typeStack.titleLabel.text = viewState.collectionPointTypeTitle(localize: Localizer())
        typeStack.setIcons(from: viewState.collectionPointTypes.map { $0.icon })
        iconStacks.addArrangedSubview(typeStack)

        let wheelchairStack = IconStackView.autoLayout()
        wheelchairStack.iconBackgroundColor = .primaryDark
        wheelchairStack.titleLabel.text = viewState.wheelChairAccessibleTitle.localized
        wheelchairStack.setIcons(from: [viewState.wheelChairAccessibleIcon])
        wheelchairStack.alpha = viewState.wheelChairAccessible ? 1 : 0.3
        iconStacks.addArrangedSubview(wheelchairStack)

        layoutIfNeeded()
    }

    private func setupCloseControl() {
        closeControl.tintColor = .accentAccent
        closeControl.setImage(UIImage(named: "ic_24_chevron_down"), for: .normal)
        closeControl.addTarget(self, action: #selector(callDelegate), for: .touchUpInside)
        stack.addArrangedSubview(closeControl)
    }
}
