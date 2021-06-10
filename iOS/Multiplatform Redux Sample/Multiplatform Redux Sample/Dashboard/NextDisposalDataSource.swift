//
//  NextDisposalDataSource.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
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
            let disposal = self.nextDisposals[indexPath.row]
            cell.configureWith(model: disposal)
        }
    }

    func numberOfSections(in tableView: UITableView) -> Int {
        1
    }

}
