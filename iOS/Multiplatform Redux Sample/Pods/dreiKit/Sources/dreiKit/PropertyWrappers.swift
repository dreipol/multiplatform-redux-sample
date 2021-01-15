//
//  PropertyWrappers.swift
//  dreiKit
//
//  Created by phil on 18.12.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import Foundation

@propertyWrapper
public struct UserDefault<T> {
    let key: String
    let defaultValue: T

    init(_ key: String, defaultValue: T) {
        self.key = key
        self.defaultValue = defaultValue
    }

    public var wrappedValue: T {
        get {
            return UserDefaults.standard.object(forKey: key) as? T ?? defaultValue
        }
        set {
            UserDefaults.standard.set(newValue, forKey: key)
        }
    }
}

public struct UserDefaultsConfig {
    @UserDefault("selected_categories", defaultValue: nil)
    static var selectedCategories: [String]?
    // swiftlint:disable:previous let_var_whitespace
}
