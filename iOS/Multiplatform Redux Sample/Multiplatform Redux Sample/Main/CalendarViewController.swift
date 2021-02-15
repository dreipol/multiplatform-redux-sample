//
//  CalendarViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit
import ReduxSampleShared

class CalendarViewController: BasePresenterViewController<CalendarView>, CalendarView {
    override var viewPresenter: Presenter<CalendarView> { CalendarViewKt.calendarPresenter }
    private var disposalsDataSource = CombinedDisposalsDataSource()

    private let disposalTableView = UITableView.autoLayout(style: .grouped)

    override init() {
        super.init()
        view.addSubview(disposalTableView)

        NSLayoutConstraint.activate([
            disposalTableView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            disposalTableView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            disposalTableView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: kUnit3),
            disposalTableView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit3),
        ])

        disposalTableView.backgroundColor = .clear
        disposalTableView.delegate = disposalsDataSource
        disposalTableView.dataSource = disposalsDataSource
        disposalTableView.register(DisposalCell.self)
        disposalTableView.register(NextDisposalCell.self)

        disposalTableView.separatorStyle = .none
        disposalTableView.separatorInset = .zero
        disposalTableView.clipsToBounds = false
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    func render(viewState_ viewState: CalendarViewState) {
        disposalsDataSource.titleText = String(format: viewState.titleReplaceable.localized, viewState.zip?.stringValue ?? "")
        disposalsDataSource.nextDisposals = viewState.disposalsState.nextDisposals
        disposalsDataSource.allDisposals = viewState.disposalsState.disposals
        disposalTableView.reloadData()
        kermit().d(viewState)
    }

}

extension CalendarViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_30_calendar" }
}
