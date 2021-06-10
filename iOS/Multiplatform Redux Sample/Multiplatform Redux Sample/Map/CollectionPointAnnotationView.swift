//
//  CollectionPointAnnotationView.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Samuel Bichsel on 04.03.21.
//

import dreiKit
import Foundation
import MapKit.MKAnnotationView
import ReduxSampleShared

class CollectionPointAnnotationView: MKAnnotationView {
    static let pin = UIImage(named: "ic_32_location")?.withTintByMultiply(with: .accentAccent)
    static let pinSelected = UIImage(named: "selected")
    static let reuseIdentifier = "CollectionPointAnnotationView"

    init(annotation: MKAnnotation) {
        super.init(annotation: annotation, reuseIdentifier: Self.reuseIdentifier)
        super.image = Self.pin
    }

    @available(*, unavailable)
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

}
