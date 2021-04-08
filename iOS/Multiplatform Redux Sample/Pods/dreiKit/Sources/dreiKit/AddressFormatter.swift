//
//  AddressFormatter.swift
//  Transport
//
//  Created by Nils Becker on 14.10.20.
//  Copyright © 2020 dreipol GmbH. All rights reserved.
//

import Foundation

public protocol QueryEncodableAddress {
    /**
     Should return the address as a string with the following format: "street housenumber,  zip city, country"
     Partials are allowed, but may lead to an inaccurate or wrong location when used with an external map app.
     */
    var queryString: String { get }
}

extension QueryEncodableAddress {
    func queryEncodedString() -> String? {
        return queryString.replacingOccurrences(of: " ", with: "+").addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
    }
}

public struct AddressString: QueryEncodableAddress {
    public let queryString: String

    public init(_ string: String) {
        queryString = string
    }
}

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

extension Address: QueryEncodableAddress {
    public var queryString: String { AddressFormatter.string(forSearch: self) }
}

public final class AddressFormatter {
    private init() {}

    static func string(forDisplaying address: Address) -> String {
        "\(address.name ?? ""), \(address.street ?? "")\n\(address.zip ?? "") \(address.city ?? "")"
    }

    static func string(forSearch address: Address) -> String {
        "\(address.street ?? ""), \(address.zip ?? "") \(address.city ?? ""), \(address.country ?? "")"
    }
}
