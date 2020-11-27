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
    private var nextDisposalsDataSource = NextDisposalDataSource()
    private var allDisposalsDataSource = AllDisposalsDataSource()
    private let titleLabel = UILabel.h2()
    private let scrollView = UIScrollView.autoLayout()
    private let vStack = UIStackView.autoLayout(axis: .vertical)
    private let nextDisposalTableView = IntrinsicTableView.autoLayout()
    private let disposalTableView = IntrinsicTableView.autoLayout()

    init() {
        super.init(nibName: nil, bundle: nil)
        scrollView.showsVerticalScrollIndicator = false
        scrollView.addSubview(vStack)
        vStack.fitVerticalScrollView()
        view.addSubview(scrollView)
        scrollView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: kUnit4, leading: kUnit3, bottom: kUnit3, trailing: kUnit3))
        vStack.alignment = .fill

        titleLabel.textAlignment = .left
        vStack.addArrangedSubview(titleLabel)

        vStack.addSpace(kUnit3)

        vStack.addArrangedSubview(nextDisposalTableView)
        nextDisposalTableView.delegate = nextDisposalsDataSource
        nextDisposalTableView.dataSource = nextDisposalsDataSource
        nextDisposalTableView.register(NextDisposalCell.self, forCellReuseIdentifier: NextDisposalCell.reuseIdentifier)
        nextDisposalTableView.separatorStyle = .none
        nextDisposalTableView.separatorInset = .zero

        vStack.addArrangedSubview(disposalTableView)
        disposalTableView.delegate = allDisposalsDataSource
        disposalTableView.dataSource = allDisposalsDataSource
        disposalTableView.register(DisposalCell.self, forCellReuseIdentifier: DisposalCell.reuseIdentifier)
        disposalTableView.separatorStyle = .none
        disposalTableView.separatorInset = .zero
        disposalTableView.sectionHeaderHeight = kUnit5
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    func render(viewState_ viewState: DashboardViewState) {
        titleLabel.text = viewState.titleReplaceable.localized.replacingOccurrences(of: "%s", with: viewState.zip?.stringValue ?? "")
        nextDisposalsDataSource.nextDisposals = viewState.disposalsState.nextDisposals
        allDisposalsDataSource.allDisposals = viewState.disposalsState.disposals
        nextDisposalTableView.reloadData()
        disposalTableView.reloadData()
        print(viewState)
    }

}

extension CalendarViewController: TabBarCompatible {
    var tabBarImageName: String { "iconIc40Calendar" }
}
