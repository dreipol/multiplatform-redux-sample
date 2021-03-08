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
    private let mapView = SwisstopoMapView(baseLayerType: .PIXELKARTE_GRAUSTUFEN)
    // swiftlint:disable force_unwrapping
    private let unselectedLayer = MCIconLayerInterface.create()!
    private let selectedLayer = MCIconLayerInterface.create()!
    // swiftlint:enable force_unwrapping
    private let unselectedTapListener = PinTapListener(kind: .unselected)
    private let selectedTapListener = PinTapListener(kind: .selected)
    private let locationControl = LocationControl.autoLayout()

    override init() {
        super.init()

        setupMapView()
        view.addSubview(locationControl)
        NSLayoutConstraint.activate([
            locationControl.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit3),
            locationControl.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -kUnit3),
        ])
        locationControl.addTarget(self, action: #selector(didTapLocationButton), for: .touchUpInside)
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    private func setupMapView() {
        mapView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(mapView)
        mapView.fitSuperview()

        mapView.camera.setMinZoom(Self.minZoom)
        mapView.camera.setMaxZoom(Self.maxZoom)
        mapView.add(layer: unselectedLayer.asLayerInterface())
        mapView.add(layer: selectedLayer.asLayerInterface())
        unselectedLayer.setCallbackHandler(unselectedTapListener)
        selectedLayer.setCallbackHandler(selectedTapListener)

        mapView.camera.move(toCenterPositionZoom: Self.zuerichCenter, zoom: Self.minZoom, animated: true)
    }

    func render(collectionPointMapViewState: CollectionPointMapViewState) {
        let selectedPoint = collectionPointMapViewState.selectedCollectionPoint
        updateIconLayer(from: collectionPointMapViewState.collectionPoints, selectedPoint: selectedPoint)
    }

    private func updateIconLayer(from collectionPoints: [CollectionPoint], selectedPoint: CollectionPointViewState?) {
        var unselectedChangeSet = PinChangeSet(kind: .unselected,
                                               layer: unselectedLayer,
                                               newPoints: collectionPoints)
        unselectedChangeSet.updateLayer()

        var selectedPoints: [CollectionPoint] = []
        if let collectionPoint = selectedPoint?.collectionPoint, let icon = collectionPoint.selectedIcon {
            kermit().d("Point selected: \(collectionPoint.id)")
            selectedPoints.append(collectionPoint)
            moveMapTo(icon.getCoordinate())
        }
        var selectedChangeSet = PinChangeSet(kind: .selected, layer: selectedLayer, newPoints: selectedPoints)
        selectedChangeSet.updateLayer()
    }

    func moveMapTo(_ coordinate: MCCoord) {
        let mapCoordinateSystem = mapView.mapInterface.getMapConfig().mapCoordinateSystem.identifier
        var mapCoordinate = coordinate
        if mapCoordinateSystem != coordinate.systemIdentifier,
           let mc = mapView.mapInterface.getCoordinateConverterHelper()?.convert(mapCoordinateSystem, coordinate: coordinate) {
            mapCoordinate = mc
        }
        mapView.camera.move(toCenterPosition: mapCoordinate, animated: true)
    }

    @objc
    private func didTapLocationButton() {
        kermit().d("Locate me!")
    }
}

extension CollectionPointMapViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_32_location" }
}
