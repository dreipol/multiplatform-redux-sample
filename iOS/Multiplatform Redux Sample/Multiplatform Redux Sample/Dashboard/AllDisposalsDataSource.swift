//
//  AllDisposalsDataSource.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit
import ReduxSampleShared

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
        label.textColor = .testAppBlue
        label.text = allDisposals[section].formattedHeader

        let headerView = UIView()
        headerView.addSubview(label)
        label.topAnchor.constraint(equalTo: headerView.topAnchor).isActive = true
        headerView.backgroundColor = .testAppGreenLight
        return headerView
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return allDisposals[section].disposalCalendarEntries.count
    }

   func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: DisposalCell.reuseIdentifier, for: indexPath) as? DisposalCell else {
            fatalError("Unexpected Cell Class")
        }

        let calenderMonth = allDisposals[indexPath.section]
        let disposal = calenderMonth.disposalCalendarEntries[indexPath.row]
        cell.configureWith(model: disposal)
        return cell
   }
}
