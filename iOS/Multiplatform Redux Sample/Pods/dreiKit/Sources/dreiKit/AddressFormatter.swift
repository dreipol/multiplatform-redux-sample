//
//  AddressFormatter.swift
//  Transport
//
//  Created by Nils Becker on 14.10.20.
//  Copyright © 2020 dreipol GmbH. All rights reserved.
//

import Foundation

public struct Address {
    let name: String?
    let street: String?
    let zip: String?
    let city: String?
    let country: String?

    public init(name: String?, street: String?, zip: String?, city: String?, country: String?) {
        self.name = name
        self.street = street
        self.zip = zip
        self.city = city
        self.country = country
    }
}

public final class AddressFormatter {
    public init() {}

    public func string(forDisplaying address: Address) -> String {
        "\(address.name ?? ""), \(address.street ?? "")\n\(address.zip ?? "") \(address.city ?? "")"
    }

    public func string(forSearch address: Address) -> String {
        "\(address.street ?? ""), \(address.zip ?? "") \(address.city ?? ""), \(address.country ?? "")"
    }

    func queryEncodedString(address: Address) -> String? {
        let query = string(forSearch: address)
        return query.replacingOccurrences(of: " ", with: "+").addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
    }
}
