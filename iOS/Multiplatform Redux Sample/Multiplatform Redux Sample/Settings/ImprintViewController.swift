//
//  ImprintViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 10.02.21.
//

import UIKit
import ReduxSampleShared

class ImprintViewController: PresenterViewController<ImprintView>, ImprintView {
    private let headerView = HeaderView()
    override var viewPresenter: Presenter<ImprintView> { ImprintViewKt.imprintPresenter }

    override init() {
        super.init()
        view.backgroundColor = .testAppGreenLight
        vStack.addArrangedSubview(headerView)

        vStack.addSpace(kUnit3)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(imprintViewState viewState: ImprintViewState) {
//        TODO
    }
}
