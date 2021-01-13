//
//  Coordinator.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import Foundation
import rezhycleShared

protocol Coordinator {
    func updateNavigationState(navigationState: NavigationState)
}
