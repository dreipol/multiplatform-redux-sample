//
//  Coordinator.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import Foundation
import ReduxSampleShared

protocol Coordinator {
    func updateNavigationState(navigationState: NavigationState)
}
