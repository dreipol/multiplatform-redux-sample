//
//  CollectionPointMapViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import MapCoreSharedModule
import ReduxSampleShared
import SwisstopoMapSDK
import UIKit

class CollectionPointMapViewController: BasePresenterViewController<CollectionPointMapView>, CollectionPointMapView {
    private static let minZoom: Double = 175000
    private static let maxZoom: Double = 2400

    private static let zuerichCenter = MCCoord(systemIdentifier: MCCoordinateSystemIdentifiers.epsg2056(), x: 2682308, y: 1248764, z: 0)

    override var viewPresenter: Presenter<CollectionPointMapView> { CollectionPointMapViewKt.collectionPointMapPresenter }
    private let titleLabel = UILabel.h2()
    private let mapView = SwisstopoMapView()
    private let iconLayer = MCIconLayerInterface.create()!

    override init() {
        super.init()
        mapView.camera.addListener(self)
        mapView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(mapView)
        mapView.fitSuperview()
        mapView.camera.setMinZoom(Self.minZoom)
        mapView.camera.setMaxZoom(Self.maxZoom)
        mapView.add(layer: iconLayer.asLayerInterface())
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        mapView.camera.move(toCenterPositionZoom: Self.zuerichCenter, zoom: Self.minZoom, animated: true)
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(collectionPointMapViewState: CollectionPointMapViewState) {
        createIconLayer(from: collectionPointMapViewState.collectionPoints)
    }

    func createIconLayer(from collectionPoints: [CollectionPoint]) {
        let icons = collectionPoints.map { $0.mapIcon }

        if icons.count > 0 {
            iconLayer.setIcons(icons)
        } else {
            iconLayer.clear()
        }
    }
}

extension CollectionPointMapViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_32_location" }
}

extension CollectionPointMapViewController: MCMapCamera2dListenerInterface {
    func onVisibleBoundsChanged(_ visibleBounds: MCRectCoord, zoom: Double) {
        print("bounds: \(visibleBounds)\n zoom: \(zoom)")
        print("center: \(mapView.camera.getCenterPosition())")
    }

    func onRotationChanged(_ angle: Float) {}
}
