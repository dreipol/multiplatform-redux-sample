//
//  SnappingCollectionViewLayout.swift
//  dreiKit
//
//  Created by Samuel Bichsel on 19.11.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import Foundation
import UIKit.UICollectionViewLayout

public class SnappingCollectionViewLayout: UICollectionViewFlowLayout {
    override public func targetContentOffset(forProposedContentOffset proposedContentOffset: CGPoint,
                                             withScrollingVelocity velocity: CGPoint) -> CGPoint {
        guard let collectionView = collectionView else {
            return super.targetContentOffset(forProposedContentOffset: proposedContentOffset, withScrollingVelocity: velocity)
        }

        var offsetAdjustment = CGFloat.greatestFiniteMagnitude
        let horizontalOffset: CGFloat = proposedContentOffset.x + collectionView.contentInset.left
        let collectionViewWidth = collectionView.bounds.size.width
        let collectionViewHeight = collectionView.bounds.size.height
        let targetRect = CGRect(x: proposedContentOffset.x, y: 0, width: collectionViewWidth, height: collectionViewHeight)

        let layoutAttributesArray = super.layoutAttributesForElements(in: targetRect)

        layoutAttributesArray?.forEach { layoutAttributes in
            let itemOffset: CGFloat = layoutAttributes.frame.origin.x
            if abs(itemOffset - horizontalOffset) < abs(offsetAdjustment) {
                offsetAdjustment = itemOffset - horizontalOffset
            }
        }

        return CGPoint(x: proposedContentOffset.x + offsetAdjustment, y: proposedContentOffset.y)
    }
}
