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
        titleLabel.font = UIFont.h1()
        vStack.addSpace(kUnit3)
        textLabel.font = UIFont.h3()
        vStack.addArrangedSubview(textLabel)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(infoViewState: InfoViewState) {
        titleLabel.attributedText = infoViewState.titleHtmlKey.localized.htmlAttributedString(size: 28, color: UIColor.testAppBlue)
        textLabel.attributedText = infoViewState.textHtmlKey.localized.htmlAttributedString(size: 18, color: UIColor.testAppBlack)
    }

}

extension InfoViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_30_info" }
}
