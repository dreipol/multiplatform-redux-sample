//
//  CombinedDisposalsDataSource.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 15.02.21.
//
import ReduxSampleShared
import UIKit.UITableView

typealias SectionDataSourceTuple = (Int, UITableViewDataSource & UITableViewDelegate)

class CombinedDisposalsDataSource: NSObject, UITableViewDataSource, UITableViewDelegate {
    private let nextDataSource = NextDisposalDataSource()
    private var allDataSource = AllDisposalsDataSource()

    var allDisposals: [DisposalCalendarMonth] {
        get { allDataSource.allDisposals }
        set { allDataSource.allDisposals = newValue }
    }

    var nextDisposals: [DisposalCalendarEntry] {
        get { nextDataSource.nextDisposals }
        set { nextDataSource.nextDisposals = newValue }
    }

    var titleText: String = ""

    func numberOfSections(in tableView: UITableView) -> Int {
        return nextDataSource.numberOfSections(in: tableView) + allDataSource.numberOfSections(in: tableView)
    }

    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        if section == 0 {
            return titleText
        } else {
            let (section, dataSource) = getDataSourceForSection(section: section)
            return dataSource.tableView?(tableView, titleForHeaderInSection: section)
        }
    }

    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if section == 0 {
            let label = UILabel.h2()
            label.text = titleText
            label.textAlignment = .left

            let headerView = UIView()
            headerView.addSubview(label)
            NSLayoutConstraint.activate([
                label.leadingAnchor.constraint(equalTo: headerView.leadingAnchor),
                label.trailingAnchor.constraint(equalTo: headerView.trailingAnchor),
                label.topAnchor.constraint(equalTo: headerView.topAnchor, constant: kUnit3),
                label.bottomAnchor.constraint(equalTo: headerView.bottomAnchor, constant: -kUnit3),
            ])
            headerView.backgroundColor = .primaryLight
            return headerView
        } else {
            let (section, dataSource) = getDataSourceForSection(section: section)
            return dataSource.tableView?(tableView, viewForHeaderInSection: section)
        }
    }

    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        let (section, dataSource) = getDataSourceForSection(section: section)
        return dataSource.tableView?(tableView, heightForHeaderInSection: section) ?? UITableView.automaticDimension
    }

    func tableView(_ tableView: UITableView, estimatedHeightForHeaderInSection section: Int) -> CGFloat {
        return section == 0 ? 108 : kTableViewHeaderHeight
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let (section, dataSource) = getDataSourceForSection(section: section)
        return dataSource.tableView(tableView, numberOfRowsInSection: section)
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let (section, dataSource) = getDataSourceForSection(section: indexPath.section)
        let newIndexPath = IndexPath(row: indexPath.row, section: section)
        return dataSource.tableView(tableView, cellForRowAt: newIndexPath)
    }

    private func getDataSourceForSection(section: Int) -> SectionDataSourceTuple {
        if section == 0 {
            return (0, nextDataSource)
        } else {
            return (section - 1, allDataSource)
        }
    }
}
