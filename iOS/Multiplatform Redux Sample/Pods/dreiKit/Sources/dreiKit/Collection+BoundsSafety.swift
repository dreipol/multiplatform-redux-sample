//
//  Collection+BoundsSafety.swift
//  dreiKit
//
//  Created by Nils Becker on 07.09.20.
//

import Foundation

public extension Collection {

    /// Returns the element at the specified index if it is within bounds, otherwise nil.
    subscript (safe index: Index) -> Element? {
        return indices.contains(index) ? self[index] : nil
    }
}
