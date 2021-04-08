//
//  Array+KotlinInit.swift
//  Multiplatform Redux Sample
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
