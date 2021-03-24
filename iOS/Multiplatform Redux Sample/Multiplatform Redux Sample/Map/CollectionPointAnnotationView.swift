//
//  CollectionPointAnnotationView.swift
//  Multiplatform Redux Sample
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
