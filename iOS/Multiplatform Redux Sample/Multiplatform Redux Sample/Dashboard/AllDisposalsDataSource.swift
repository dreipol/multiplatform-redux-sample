//
//  AllDisposalsDataSource.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit
import ReduxSampleShared

class AllDisposalsDataSource: NSObject, UITableViewDataSource, UITableViewDelegate {

   var allDisposals = [DisposalNotification]()

   func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
       return allDisposals.count
   }

   func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard let cell = tableView.dequeueReusableCell(withIdentifier: DisposalCell.reuseIdentifier, for: indexPath) as? DisposalCell else {
        fatalError("Unexpected Cell Class")
    }

    let disposal = allDisposals[indexPath.row]
    cell.textLabel?.text = disposal.disposal.disposalType.translationKey.localized
    return cell
   }
}
