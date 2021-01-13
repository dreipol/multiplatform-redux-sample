//
//  WeakArrayPropertyWrapper.swift
//  dreiKit
//
//  Created by Nils Becker on 14.12.20.
//

import Foundation

private struct WeakElement<T: AnyObject> {
    weak var element: T?

    init(_ element: T) {
        self.element = element
    }
}

@propertyWrapper
public struct Weak<T: AnyObject> {
    private var array = [WeakElement<T>]()

    public init() {}

    public var wrappedValue: [T] {
        get {
            array.compactMap(\.element)
        }
        set {
            array = newValue.map({ WeakElement($0) })
        }
    }
}
