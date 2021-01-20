//
//  CalendarViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit
import ReduxSampleShared

class CalendarViewController: PresenterViewController<CalendarView>, CalendarView {
    override var viewPresenter: Presenter<CalendarView> { CalendarViewKt.calendarPresenter }
    private var nextDisposalsDataSource = NextDisposalDataSource()
    private var allDisposalsDataSource = AllDisposalsDataSource()
    private let titleLabel = UILabel.h2()

    private let nextDisposalTableView = IntrinsicTableView.autoLayout()
    private let disposalTableView = IntrinsicTableView.autoLayout()

    override init() {
        super.init()

        titleLabel.textAlignment = .left
        vStack.addArrangedSubview(titleLabel)

        vStack.addSpace(kUnit3)

        vStack.addArrangedSubview(nextDisposalTableView)
        nextDisposalTableView.delegate = nextDisposalsDataSource
        nextDisposalTableView.dataSource = nextDisposalsDataSource
        nextDisposalTableView.register(NextDisposalCell.self, forCellReuseIdentifier: NextDisposalCell.reuseIdentifier)
        nextDisposalTableView.separatorStyle = .none
        nextDisposalTableView.separatorInset = .zero
        nextDisposalTableView.clipsToBounds = false

        vStack.addArrangedSubview(disposalTableView)
        disposalTableView.delegate = allDisposalsDataSource
        disposalTableView.dataSource = allDisposalsDataSource
        disposalTableView.register(DisposalCell.self, forCellReuseIdentifier: DisposalCell.reuseIdentifier)
        disposalTableView.separatorStyle = .none
        disposalTableView.separatorInset = .zero
        disposalTableView.sectionHeaderHeight = kUnit5
        disposalTableView.clipsToBounds = false
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    func render(viewState_ viewState: CalendarViewState) {
        titleLabel.text = String(format: viewState.titleReplaceable.localized, viewState.zip?.stringValue ?? "")
        nextDisposalsDataSource.nextDisposals = viewState.disposalsState.nextDisposals
        allDisposalsDataSource.allDisposals = viewState.disposalsState.disposals
        nextDisposalTableView.reloadData()
        disposalTableView.reloadData()
        print(viewState)
    }

}

extension CalendarViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_30_calendar" }
}
