//
//  LocationPermissionManager.swift
//  dreiKit
//
//  Created by Nils Becker on 08.04.21.
//

import CoreLocation

public extension CLAuthorizationStatus {
    var isLocationAvailable: Bool {
        switch self {
        case .authorizedAlways, .authorizedWhenInUse, .authorized:
            return true
        default:
            return false
        }
    }
}

public class LocationPermissionManager: NSObject, CLLocationManagerDelegate {
    let manager = CLLocationManager()
    var callbacks = [(CLAuthorizationStatus) -> Void]()

    public override init() {
        super.init()
        manager.delegate = self
    }

    public func requestPermissionIfNeeded(callback: @escaping (CLAuthorizationStatus) -> Void) {
        let status = CLLocationManager.authorizationStatus()
        guard status == .notDetermined else {
            callback(status)
            return
        }
        callbacks.append(callback)
        manager.requestWhenInUseAuthorization()
    }

    public func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        guard status != .notDetermined else {
            return
        }

        for callback in callbacks {
            callback(status)
        }
        callbacks = []
    }
}
