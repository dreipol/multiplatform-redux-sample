//
//  AllDisposalsDataSource.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit
import ReduxSampleShared

class AllDisposalsDataSource: NSObject, UITableViewDataSource, UITableViewDelegate {
    var allDisposals: [String: [DisposalCalendarEntry]] = [:]

    func numberOfSections(in tableView: UITableView) -> Int {
        return allDisposals.keys.count
    }

    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return Array(allDisposals.keys)[section]
    }

    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let label = UILabel.h3()
        label.textColor = .testAppBlue
        label.text = Array(allDisposals.keys)[section]

        let headerView = UIView()
        headerView.addSubview(label)
        label.topAnchor.constraint(equalTo: headerView.topAnchor).isActive = true
        headerView.backgroundColor = .testAppGreenLight
        return headerView
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let key = Array(allDisposals.keys)[section]
        return allDisposals[key]?.count ?? 0
    }

   func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: DisposalCell.reuseIdentifier, for: indexPath) as? DisposalCell else {
            fatalError("Unexpected Cell Class")
        }

        let disposal = Array(allDisposals.values)[indexPath.section][indexPath.row]
        cell.configureWith(model: disposal)
        return cell
   }
}
