//
//  CollectionPointMapViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import dreiKit
import MapKit
import ReduxSampleShared
import UIKit

class CollectionPointMapViewController: BasePresenterViewController<CollectionPointMapView>, CollectionPointMapView {
    private static let minZoom: Double = 175_000
    private static let maxZoom: Double = 2400
    private static let zuerichCenter = CLLocationCoordinate2D(latitude: 47.3744489, longitude: 8.5410422)

    override var viewPresenter: Presenter<CollectionPointMapView> { CollectionPointMapViewKt.collectionPointMapPresenter }
    private let titleLabel = UILabel.h2()
    private let mapView = MKMapView.autoLayout()
    private let locationControl = LocationControl.autoLayout()
    private let infoView = CollectionPointInfoView.autoLayout()
    private var infoViewConstraintInactive: NSLayoutConstraint!
    private var infoViewConstraintActive: NSLayoutConstraint!

    override init() {
        super.init()
        setupMapView()
        locationControl.isHidden = true
        view.addSubview(locationControl)
        setupInfoView()

        NSLayoutConstraint.activate([
            locationControl.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit3),
            locationControl.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -kUnit3),

            infoView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: kUnit3),
            infoView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit3),
            infoViewConstraintInactive
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

        mapView.setRegion(MKCoordinateRegion(center: Self.zuerichCenter, latitudinalMeters: 10_000, longitudinalMeters: 10_000),
                          animated: true)
        mapView.delegate = self
    }

    private func setupInfoView() {
        view.addSubview(infoView)
        infoViewConstraintInactive = infoView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor)
        infoViewConstraintActive = infoView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: 4)
        infoView.activeBottomConstraint = infoViewConstraintActive
        infoView.delegate = self
        infoView.addRecognizer()
    }

    func render(collectionPointMapViewState: CollectionPointMapViewState) {
        let selectedPoint = collectionPointMapViewState.selectedCollectionPoint

        var pinChangeSet = PinChangeSet(mapView: mapView, collectionPoints: collectionPointMapViewState.collectionPoints)
        pinChangeSet.updateAnnotations(selection: selectedPoint?.collectionPoint)

        if let selectedViewState = selectedPoint {
            mapView.setCenter(selectedViewState.collectionPoint.coordinate, animated: true)
            infoView.render(selectedViewState)
        }
        togglenfoView(shouldShow: selectedPoint != nil)
    }

    private func togglenfoView(shouldShow: Bool) {
        guard shouldShow != infoViewConstraintActive.isActive else {
            return
        }

        self.infoViewConstraintActive.isActive = shouldShow
        self.infoViewConstraintInactive.isActive = !shouldShow
        Animation.appearance {
            self.view.layoutIfNeeded()
        }
    }

    @objc
    private func didTapLocationButton() {
        kermit().d("Locate me!")
    }
}

extension CollectionPointMapViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_32_location" }
}

extension CollectionPointMapViewController: CollectionPointInfoViewDelegate {
    func didTapMapLink(view: CollectionPointInfoView, address: AddressString) {
        let navigationManager = StreetNavigationManager(viewController: self)
        navigationManager.showStreetDirections(to: address,
                                               directionMode: .walking,
                                               appChoicePrompt: "choose_navigation_app".localized,
                                               cancelTitle: "cancel_button".localized,
                                               appPickerSourceView: view.mapLink)
    }

    func hide(view: PanGestureView) {
        _ = dispatch(DeselectCollectionPointAction(collectionPointId: nil))
    }
}

extension CollectionPointMapViewController: MKMapViewDelegate {
    func mapView(_ mapView: MKMapView, didSelect view: MKAnnotationView) {
        guard let collectionPoint = view.annotation as? CollectionPoint else {
            return
        }
        _ = dispatch(SelectCollectionPointAction(collectionPointId: collectionPoint.id))
        view.image = CollectionPointAnnotationView.pinSelected
    }

    func mapView(_ mapView: MKMapView, didDeselect view: MKAnnotationView) {
        guard let collectionPoint = view.annotation as? CollectionPoint else {
            return
        }
        _ = dispatch(DeselectCollectionPointAction(collectionPointId: collectionPoint.id))
        view.image = CollectionPointAnnotationView.pin
    }

    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        return CollectionPointAnnotationView(annotation: annotation)
    }
}
