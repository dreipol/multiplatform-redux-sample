//
//  NextDisposalDataSource.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit
import ReduxSampleShared

class NextDisposalDataSource: NSObject, UITableViewDataSource, UITableViewDelegate {

   var nextDisposals = [DisposalNotification]()

   func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
       return nextDisposals.count
   }

   func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard let cell = tableView.dequeueReusableCell(withIdentifier: NextDisposalCell.reuseIdentifier,
                                                   for: indexPath) as? NextDisposalCell else {
        fatalError("Unexpected Cell Class")
    }

    let disposal = nextDisposals[indexPath.row]
    cell.textLabel?.text = disposal.disposal.disposalType.translationKey.localized
    return cell
   }
}
