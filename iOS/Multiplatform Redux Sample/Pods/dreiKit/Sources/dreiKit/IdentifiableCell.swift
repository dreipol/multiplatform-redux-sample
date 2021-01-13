//
//  IdentifiableCell.swift
//  dreiKit
//
//  Created by Samuel Bichsel on 22.11.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import Foundation

public protocol IdentifiableCell {
    static var cellIdentifier: String { get }
}
