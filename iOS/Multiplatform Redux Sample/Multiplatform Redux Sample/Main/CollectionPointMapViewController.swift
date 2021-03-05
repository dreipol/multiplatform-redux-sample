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
    // swiftlint:disable:next force_unwrapping
    private let iconLayer = MCIconLayerInterface.create()!

    override init() {
        super.init()

        mapView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(mapView)
        mapView.fitSuperview()

        mapView.camera.setMinZoom(Self.minZoom)
        mapView.camera.setMaxZoom(Self.maxZoom)
        mapView.add(layer: iconLayer.asLayerInterface())
        iconLayer.setCallbackHandler(self)

        mapView.camera.move(toCenterPositionZoom: Self.zuerichCenter, zoom: Self.minZoom, animated: true)
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(collectionPointMapViewState: CollectionPointMapViewState) {
        let selectedPoint = collectionPointMapViewState.selectedCollectionPoint
        updateIconLayer(from: collectionPointMapViewState.collectionPoints, selectedPoint: selectedPoint)
    }

    func updateIconLayer(from collectionPoints: [CollectionPoint], selectedPoint: CollectionPointViewState?) {
        let icons = collectionPoints.filter { $0 != selectedPoint?.collectionPoint }.compactMap { $0.unselectedIcon }

        if icons.count > 0 {
            iconLayer.setIcons(icons)
        } else {
            iconLayer.clear()
        }

        if let selectedPoint = selectedPoint {
            iconLayer.add(selectedPoint.collectionPoint.selectedIcon)
        }
    }
}

extension CollectionPointMapViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_32_location" }
}

extension CollectionPointMapViewController: MCIconLayerCallbackInterface {
    func onClickConfirmed(_ icons: [MCIconInfoInterface]) -> Bool {
        guard let icon = icons.first else {
            return false
        }
        DispatchQueue.main.async {
            _ = dispatch(SelectCollectionPointAction(collectionPointId: icon.getIdentifier()))
        }
        return true
    }
}
