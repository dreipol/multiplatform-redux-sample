//
//  AllDisposalsDataSource.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit
import ReduxSampleShared

class AllDisposalsDataSource: NSObject, UITableViewDataSource, UITableViewDelegate {

    struct MonthSection {
        var month: String
        var disposalNotifications: [DisposalNotification]
    }

    var sections = [MonthSection]()

    var allDisposals: [DisposalNotification] = [] {
        didSet {
            let groups = Dictionary(grouping: allDisposals) { (disposal) in
                return disposal.formattedHeader
            }
            sections = groups.map { (key, values) in
                return MonthSection(month: key, disposalNotifications: values)
            }
        }
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        return sections.count
    }

    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return sections[section].month
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return sections[section].disposalNotifications.count
    }

   func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: DisposalCell.reuseIdentifier, for: indexPath) as? DisposalCell else {
            fatalError("Unexpected Cell Class")
        }

        let disposal = allDisposals[indexPath.row]
        cell.configureWith(model: disposal)
        return cell
   }
}
