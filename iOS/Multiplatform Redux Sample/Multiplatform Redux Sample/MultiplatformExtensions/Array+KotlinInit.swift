//
//  Array+KotlinInit.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Samuel Bichsel on 10.03.21.
//

import Foundation
import ReduxSampleShared

// https://youtrack.jetbrains.com/issue/KT-41238
extension Array where Element: AnyObject {
    init(_ kotlinArray: KotlinArray<Element>) {
        self.init()
        let iterator = kotlinArray.iterator()
        while iterator.hasNext() {
            // swiftlint:disable force_cast
            self.append(iterator.next() as! Element)
        }
    }
}
