//
//  CollectionPointMapViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import MapCoreSharedModule
import MapKit
import ReduxSampleShared
import SwisstopoMapSDK
import UIKit

private let zurichLat = 47.3744489
private let zurichLon = 8.5410422

class CollectionPointMapViewController: BasePresenterViewController<CollectionPointMapView>, CollectionPointMapView {
    override var viewPresenter: Presenter<CollectionPointMapView> { CollectionPointMapViewKt.collectionPointMapPresenter }
    private let titleLabel = UILabel.h2()
    private var mapView = SwisstopoMapView()

    override init() {
        super.init()
        mapView.camera.addListener(self)
        mapView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(mapView)
        mapView.fitSuperview()
        mapView.camera.move(toCenterPosition: .init(systemIdentifier: MCCoordinateSystemIdentifiers.epsg4326(),
                                                    x: zurichLon,
                                                    y: zurichLat,
                                                    z: 0), animated: false)
        mapView.camera.setZoom(174901, animated: false)
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(collectionPointMapViewState: CollectionPointMapViewState) {}
}

extension CollectionPointMapViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_32_location" }
}

extension CollectionPointMapViewController: MCMapCamera2dListenerInterface {
    func onVisibleBoundsChanged(_ visibleBounds: MCRectCoord, zoom: Double) {
        print("bounds: \(visibleBounds)\n zoom: \(zoom)")
    }

    func onRotationChanged(_ angle: Float) {}
}
