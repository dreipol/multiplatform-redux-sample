//
//  ImprintViewController.swift
//  Multiplatform Redux Sample
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
