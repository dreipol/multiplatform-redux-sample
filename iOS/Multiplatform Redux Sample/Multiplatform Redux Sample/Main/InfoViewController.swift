//
//  InfoViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit
import ReduxSampleShared

class InfoViewController: PresenterViewController<InfoView>, InfoView {
    override var viewPresenter: Presenter<CalendarView> { InfoViewKt.infoPresenter }
    private let titleLabel = LinkableTextView.autoLayout()
    private let textLabel = LinkableTextView.autoLayout()

    override init() {
        super.init()
        vStack.addSpace(kUnit3)
        vStack.addArrangedSubview(titleLabel)

        let linkAttributes: [NSAttributedString.Key: Any] = [
            NSAttributedString.Key.foregroundColor: UIColor.accentDarkAccent,
        ]
        titleLabel.font = UIFont.h1()
        titleLabel.linkTextAttributes = linkAttributes
        vStack.addSpace(kUnit3)
        textLabel.font = UIFont.h3()
        textLabel.linkTextAttributes = linkAttributes
        vStack.addArrangedSubview(textLabel)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(infoViewState: InfoViewState) {
        titleLabel.attributedText = infoViewState.titleHtmlKey.localized.htmlAttributedString(size: 28, color: .primaryDark)
        textLabel.attributedText = infoViewState.textHtmlKey.localized.htmlAttributedString(size: 18, color: .primaryDark)
    }

}

extension InfoViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_30_info" }
}
