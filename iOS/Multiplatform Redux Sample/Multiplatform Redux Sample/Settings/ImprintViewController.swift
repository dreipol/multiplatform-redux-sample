//
//  ImprintViewController.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 10.02.21.
//

import UIKit
import ReduxSampleShared

class ImprintViewController: StackPresenterViewController<ImprintView>, ImprintView {
    private let headerView = HeaderView()
    private let contentText = LinkableTextView.autoLayout()
    override var viewPresenter: Presenter<ImprintView> { ImprintViewKt.imprintPresenter }

    override init() {
        super.init()
        view.backgroundColor = .white
        contentText.backgroundColor = .clear

        vStack.addArrangedSubview(headerView)
        vStack.addSpace(kUnit2)

        let linkAttributes: [NSAttributedString.Key: Any] = [
            NSAttributedString.Key.foregroundColor: UIColor.accentDarkAccent,
        ]
        contentText.font = UIFont.h3()
        contentText.linkTextAttributes = linkAttributes
        vStack.addArrangedSubview(contentText)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(imprintViewState: ImprintViewState) {
        headerView.titleLabel.text = imprintViewState.headerViewState.title.localized
        contentText.attributedText = imprintViewState.contentHtmlKey.localized.htmlAttributedString(size: 18, color: .primaryDark)
    }
}
