//
//  UITableView+Tools.swift
//  dreiKit
//
//  Created by Samuel Bichsel on 21.11.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import Foundation
import UIKit.UITableView

public typealias IdentifiableTableViewCell = UITableViewCell&IdentifiableCell

public extension UITableView {
    func register(_ cellClass: IdentifiableTableViewCell.Type) {
        register(cellClass, forCellReuseIdentifier: cellClass.cellIdentifier)
    }

    func dequeueReusableCell<CellType: IdentifiableTableViewCell>(withClass cellClass: CellType.Type,
                                                                  indexPath: IndexPath,
                                                                  updateFunc: ((CellType) -> Void)? = nil) -> UITableViewCell {
        let cell = dequeueReusableCell(withIdentifier: cellClass.cellIdentifier, for: indexPath)
        guard  let typedCell = cell as? CellType else {
            return cell
        }
        updateFunc?(typedCell)
        return typedCell
    }
}
