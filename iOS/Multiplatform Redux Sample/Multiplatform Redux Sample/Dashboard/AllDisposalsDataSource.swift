//
//  AllDisposalsDataSource.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import ReduxSampleShared
import UIKit

class AllDisposalsDataSource: NSObject, UITableViewDataSource, UITableViewDelegate {
    var allDisposals: [DisposalCalendarMonth] = []

    func numberOfSections(in tableView: UITableView) -> Int {
        return allDisposals.count
    }

    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return allDisposals[section].formattedHeader
    }

    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let label = UILabel.h3()
        label.text = allDisposals[section].formattedHeader

        let headerView = UIView()
        headerView.addSubview(label)
        label.bottomAnchor.constraint(equalTo: headerView.bottomAnchor, constant: -kUnit2).isActive = true
        headerView.backgroundColor = .primaryLight
        return headerView
    }

    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if section == 0 {
            return kTableViewHeaderHeight - kUnit1
        } else {
            return kTableViewHeaderHeight
        }
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return allDisposals[section].disposalCalendarEntries.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        return tableView.dequeueReusableCell(withClass: DisposalCell.self, indexPath: indexPath) { [unowned self] cell in
            let calenderMonth = self.allDisposals[indexPath.section]
            let disposal = calenderMonth.disposalCalendarEntries[indexPath.row]
            cell.configureWith(model: disposal)
        }
    }
}
