//
//  NextDisposalDataSource.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit
import ReduxSampleShared

class NextDisposalDataSource: NSObject, UITableViewDataSource, UITableViewDelegate {
    var nextDisposals = [DisposalCalendarEntry]()

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return nextDisposals.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        return tableView.dequeueReusableCell(withClass: NextDisposalCell.self,
                                             indexPath: indexPath) { [unowned self] cell in
            let disposal = nextDisposals[indexPath.row]
            cell.configureWith(model: disposal)
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        1
    }

}
