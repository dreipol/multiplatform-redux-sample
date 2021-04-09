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
import GoogleMapsTileOverlay
import MaterialComponents.MaterialChips

private let zoomRange = MKMapView.CameraZoomRange(minCenterCoordinateDistance: 150, maxCenterCoordinateDistance: 50_000)
private let zuerichCenter = CLLocationCoordinate2D(latitude: 47.3744489, longitude: 8.5410422)

class CollectionPointMapViewController: BasePresenterViewController<CollectionPointMapView>, CollectionPointMapView {
    var filter: [MapFilterItem] {
        get {
            Array(CollectionPointType.values()).map { type in
                MapFilterItem(collectionPointType: type, isSelected: filterViews[type]?.isSelected ?? false)
            }
        }
        set {
            for filter in newValue {
                let chip = filterViews[filter.collectionPointType]
                chip?.isSelected = filter.isSelected
                chip?.accessoryView?.isHidden = !filter.isSelected
            }
        }
    }

    override var viewPresenter: Presenter<CollectionPointMapView> { CollectionPointMapViewKt.collectionPointMapPresenter }

    private let titleLabel = UILabel.h2()
    private let mapView = MKMapView.autoLayout()
    private let locationControl = LocationControl.autoLayout()
    private let infoView = CollectionPointInfoView.autoLayout()
    private var infoViewConstraintInactive: NSLayoutConstraint!
    private var infoViewConstraintActive: NSLayoutConstraint!
    private var filterViews = [CollectionPointType: MDCChipView]()
    private let permissionManager = LocationPermissionManager()

    override init() {
        super.init()
        setupMapView()
        view.addSubview(locationControl)
        setupInfoView()

        setupFilters()

        NSLayoutConstraint.activate([
            locationControl.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit3),
            locationControl.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -kUnit3),

            infoView.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: kUnit3),
            infoView.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit3),
            infoViewConstraintInactive,
        ])
        locationControl.addTarget(self, action: #selector(didTapLocationButton), for: .touchUpInside)
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    private func setupMapView() {
        view.addSubview(mapView)
        mapView.fitSuperview()
        mapView.setRegion(MKCoordinateRegion(center: zuerichCenter, latitudinalMeters: 10_000, longitudinalMeters: 10_000), animated: false)
        mapView.showsUserLocation = true
        mapView.delegate = self
        mapView.mapType = .mutedStandard
        mapView.overrideUserInterfaceStyle = .light

        mapView.setCameraZoomRange(zoomRange, animated: false)
        mapView.pointOfInterestFilter = MKPointOfInterestFilter(including: [])
        styleMap()
    }

    private func styleMap() {
        guard let jsonURL = Bundle.main.url(forResource: "MapStyle", withExtension: "json"),
              let tileOverlay = try? GoogleMapsTileOverlay(jsonURL: jsonURL) else {
            return
        }

        tileOverlay.canReplaceMapContent = true
        mapView.addOverlay(tileOverlay, level: .aboveLabels)
    }

    private func setupInfoView() {
        view.addSubview(infoView)
        infoViewConstraintInactive = infoView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor)
        infoViewConstraintActive = infoView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: 4)
        infoView.activeBottomConstraint = infoViewConstraintActive
        infoView.delegate = self
        infoView.addRecognizer()
    }

    private func setupFilters() {
        let filtersView = UIView.autoLayout()
        filtersView.backgroundColor = .white
        filtersView.addDefaultShadow()

        let filtersStack = UIStackView.autoLayout(axis: .horizontal)
        filtersStack.spacing = kUnit2

        let filters = Array(CollectionPointType.values())
        for (i, filter) in filters.enumerated() {
            let chipView = MDCChipView.filterChip(for: filter)
            chipView.tag = i

            chipView.addTarget(self, action: #selector(didTabFilterChip(_:)), for: .touchUpInside)

            filterViews[filter] = chipView
            filtersStack.addArrangedSubview(chipView)
        }

        filtersView.addSubview(filtersStack)
        view.addSubview(filtersView)

        NSLayoutConstraint.activate([
            filtersView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            filtersView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            filtersView.topAnchor.constraint(equalTo: view.topAnchor),
            filtersView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: kUnit9),

            filtersStack.leadingAnchor.constraint(equalTo: filtersView.leadingAnchor, constant: kUnit3),
            filtersStack.trailingAnchor.constraint(lessThanOrEqualTo: filtersView.trailingAnchor, constant: -kUnit3),
            filtersStack.bottomAnchor.constraint(equalTo: filtersView.bottomAnchor, constant: -kUnit2),
            filtersStack.heightAnchor.constraint(equalToConstant: 36),
        ])
    }

    private func render(selectedPoint: CollectionPointViewState?) {
        if let selectedViewState = selectedPoint {
            mapView.setCenter(selectedViewState.collectionPoint.coordinate, animated: true)
            infoView.render(selectedViewState)
        }
        toggleInfoView(shouldShow: selectedPoint != nil)
    }

    func render(collectionPointMapViewState: CollectionPointMapViewState) {
        filter = collectionPointMapViewState.filter
        let selectedPoint = collectionPointMapViewState.selectedCollectionPoint

        let isEmptyMap = mapView.annotations.isEmpty

        let pinChangeSet = PinChangeSet(mapView: mapView, collectionPoints: collectionPointMapViewState.filteredCollectionPoints)
        pinChangeSet.updateAnnotations(selection: selectedPoint?.collectionPoint)

        if isEmptyMap {
            mapView.showAnnotations(mapView.annotations, animated: true)
        }

        render(selectedPoint: selectedPoint)
    }

    private func toggleInfoView(shouldShow: Bool) {
        guard shouldShow != infoViewConstraintActive.isActive else {
            return
        }

        self.infoViewConstraintActive.isActive = shouldShow
        self.infoViewConstraintInactive.isActive = !shouldShow
        Animation.appearance {
            self.view.layoutIfNeeded()
        }
    }

    func cycleUserTracking() {
        switch mapView.userTrackingMode {
        case .none:
            mapView.setUserTrackingMode(.follow, animated: true)
        case .follow:
            mapView.setUserTrackingMode(.followWithHeading, animated: true)
        case .followWithHeading:
            mapView.setUserTrackingMode(.none, animated: true)
        @unknown default:
            mapView.setUserTrackingMode(.none, animated: true)
        }
    }

    @objc
    private func didTapLocationButton() {
        permissionManager.requestPermissionIfNeeded { [weak self] permission in
            if permission.isLocationAvailable {
                self?.cycleUserTracking()
            } else {
                let alert = UIAlertController(title: nil, message: "location_denied_alert_text".localized, preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "to_settings_button".localized, style: .default, handler: { _ in
                    alert.dismiss(animated: true)
                    if let settingsUrl = URL(string: UIApplication.openSettingsURLString) {
                        UIApplication.shared.open(settingsUrl)
                    }
                }))
                alert.addAction(UIAlertAction(title: "cancel_button".localized, style: .cancel, handler: { _ in
                    alert.dismiss(animated: true)
                }))
                self?.present(alert, animated: true)
            }
        }
    }

    @objc private func didTabFilterChip(_ chip: MDCChipView) {
        var filter = self.filter
        let changedFilter = filter[chip.tag]
        filter[chip.tag] = MapFilterItem(collectionPointType: changedFilter.collectionPointType, isSelected: !chip.isSelected)
        _ = dispatch(UpdateFilterAction(newFilter: filter))
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
        guard !(annotation is MKUserLocation) else {
            return nil
        }
        return CollectionPointAnnotationView(annotation: annotation)
    }

    func mapView(_ mapView: MKMapView, rendererFor overlay: MKOverlay) -> MKOverlayRenderer {
        if let tileOverlay = overlay as? MKTileOverlay {
            return MKTileOverlayRenderer(tileOverlay: tileOverlay)
        }
        return MKOverlayRenderer(overlay: overlay)
    }

    func mapView(_ mapView: MKMapView, didChange mode: MKUserTrackingMode, animated: Bool) {
        locationControl.trackingType = mode
    }
}
