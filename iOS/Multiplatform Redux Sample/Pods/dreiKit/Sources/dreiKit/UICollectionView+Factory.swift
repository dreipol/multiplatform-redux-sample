//
// Created by Samuel Bichsel on 20.11.19.
// Copyright (c) 2019 dreipol. All rights reserved.
//

import Foundation
import UIKit.UICollectionView

public extension UICollectionView {
    static func createHorizontal(with layout: UICollectionViewFlowLayout, itemSize: CGSize) -> UICollectionView {
        layout.scrollDirection = .horizontal
        layout.sectionInset = .zero
        layout.itemSize = itemSize
        let collectionView: UICollectionView = UICollectionView(frame: .zero, collectionViewLayout: layout)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        return collectionView
    }
}
