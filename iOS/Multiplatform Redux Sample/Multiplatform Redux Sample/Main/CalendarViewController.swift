//
//  CalendarViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit
import ReduxSampleShared

class CalendarViewController: PresenterViewController<DashboardView>, DashboardView {
    override var viewPresenter: Presenter<DashboardView> { DashboardViewKt.dashboardPresenter }
    private let titleLabel = UILabel.h2()
    private let scrollView = UIScrollView.autoLayout()
    private let vStack = UIStackView.autoLayout(axis: .vertical)

    init() {
        super.init(nibName: nil, bundle: nil)
        scrollView.addSubview(vStack)
        vStack.fitVerticalScrollView()
        view.addSubview(scrollView)
        scrollView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: kUnit4, leading: kUnit3, bottom: kUnit3, trailing: kUnit3))
        vStack.alignment = .fill

        titleLabel.textAlignment = .left
        vStack.addArrangedSubview(titleLabel)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    func render(viewState_ viewState: DashboardViewState) {
        titleLabel.text = viewState.titleReplaceable.localized.replacingOccurrences(of: "%s", with: viewState.zip?.stringValue ?? "")
        print(viewState)
    }

}

extension CalendarViewController: TabBarCompatible {
    var tabBarImageName: String { "iconIc40Calendar" }
}
