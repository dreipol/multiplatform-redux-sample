//
//  CollectionPointMapViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit
import ReduxSampleShared
import MapKit

private let zurichLat = 47.3744489
private let zurichLon = 8.5410422

class CollectionPointMapViewController: BasePresenterViewController<CollectionPointMapView>, CollectionPointMapView {
    override var viewPresenter: Presenter<CollectionPointMapView> { CollectionPointMapViewKt.collectionPointMapPresenter }
    private let titleLabel = UILabel.h2()
    private let mapView = MKMapView()

    override init() {
        super.init()

        mapView.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(mapView)
        mapView.fitSuperview()
        let zurich = CLLocationCoordinate2D(latitude: zurichLat, longitude: zurichLon)
        mapView.setRegion(MKCoordinateRegion(center: zurich, latitudinalMeters: 10_000, longitudinalMeters: 10_000), animated: true)

        let container = UIView.autoLayout()
        container.backgroundColor = .white
        container.layer.cornerRadius = 5
        container.layer.masksToBounds = true

        titleLabel.text = "map_work_in_progress".localized
        container.addSubview(titleLabel)
        titleLabel.fillSuperviewMargins()

        view.addSubview(container)
        NSLayoutConstraint.activate([
            container.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            container.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            container.widthAnchor.constraint(equalTo: view.widthAnchor, constant: -2 * kUnit2),
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(collectionPointMapViewState: CollectionPointMapViewState) {

    }

}

extension CollectionPointMapViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_32_location" }
}
