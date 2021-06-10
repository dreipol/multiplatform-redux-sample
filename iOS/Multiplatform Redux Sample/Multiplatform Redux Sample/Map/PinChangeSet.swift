//
//  PinChangeSet.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Samuel Bichsel on 05.03.21.
//

import Foundation
import MapKit
import ReduxSampleShared

struct PinChangeSet {
    let mapView: MKMapView
    let newPoints: Set<CollectionPoint>
    let existing: Set<CollectionPoint>

    init(mapView: MKMapView, collectionPoints: [CollectionPoint]) {
        self.mapView = mapView
        self.newPoints = Set(collectionPoints)
        existing = Set(mapView.annotations.compactMap { $0 as? CollectionPoint })
    }

    private var add: Set<CollectionPoint> { newPoints.subtracting(existing) }
    private var remove: Set<CollectionPoint> { existing.subtracting(newPoints) }

    func updateAnnotations(selection: CollectionPoint?) {
        mapView.addAnnotations(Array(add))
        mapView.removeAnnotations(Array(remove))
        update(selection: selection)
    }

    func update(selection: CollectionPoint?) {
        mapView.selectedAnnotations.filter { annotation in
            guard let newSelection = selection, let preSelected = annotation as? CollectionPoint else {
                return true
            }
            return preSelected != newSelection
        }
        .forEach { self.mapView.deselectAnnotation($0, animated: true) }

        guard let newSelection = selection else {
            return
        }
        mapView.selectAnnotation(newSelection, animated: true)
    }
}
