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
        iconLayer.setCallbackHandler(self)
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
        let selectedPoint = collectionPointMapViewState.selectedCollectionPoint
        createIconLayer(from: collectionPointMapViewState.collectionPoints, selectedPoint: selectedPoint)
    }

    func createIconLayer(from collectionPoints: [CollectionPoint], selectedPoint: CollectionPointViewState?) {
        let icons = collectionPoints.filter({ $0 != selectedPoint?.collectionPoint }).map { $0.unselectedIcon }

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

extension CollectionPointMapViewController: MCMapCamera2dListenerInterface {
    func onVisibleBoundsChanged(_ visibleBounds: MCRectCoord, zoom: Double) {
        print("bounds: \(visibleBounds)\n zoom: \(zoom)")
        print("center: \(mapView.camera.getCenterPosition())")
    }

    func onRotationChanged(_ angle: Float) {}
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
