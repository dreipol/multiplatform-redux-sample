//
//  CollectionPoint+MKAnnotation.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 04.03.21.
//

import dreiKit
import Foundation
import MapKit.MKAnnotation
import ReduxSampleShared

extension CollectionPoint: MKAnnotation {
    public var coordinate: CLLocationCoordinate2D { CLLocationCoordinate2D(latitude: lat, longitude: lon) }
}
