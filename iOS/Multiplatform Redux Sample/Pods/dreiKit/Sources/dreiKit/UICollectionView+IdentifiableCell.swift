//
//  UITableView+Tools.swift
//  dreiKit
//
//  Created by Samuel Bichsel on 21.11.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import Foundation
import UIKit.UICollectionView

public typealias IdentifiableCollectionViewCell = UICollectionViewCell&IdentifiableCell

public extension UICollectionView {
    func register(_ cellClass: IdentifiableCollectionViewCell.Type) {
        register(cellClass, forCellWithReuseIdentifier: cellClass.cellIdentifier)
    }

    func dequeueReusableCell<CellType: IdentifiableCollectionViewCell>(withClass cellClass: CellType.Type,
                                                                       indexPath: IndexPath,
                                                                       updateFunc: ((CellType) -> Void)? = nil) -> UICollectionViewCell {
        let cell = dequeueReusableCell(withReuseIdentifier: cellClass.cellIdentifier, for: indexPath)
        guard  let typedCell = cell as? CellType else {
            return cell
        }
        updateFunc?(typedCell)
        return typedCell
    }
}
