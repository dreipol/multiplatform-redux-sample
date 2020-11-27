//
//  MainCoordinator.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import Foundation
import ReduxSampleShared
import UIKit.UINavigationController

class MainCoordinator: SubCoordinator, Coordinator {
    func updateNavigationState(navigationState: NavigationState) {
        if !(rootCoordinator.rootViewController is UINavigationController) {
            let navController = UINavigationController(rootViewController: MainViewController())
            navController.isNavigationBarHidden = true
            rootCoordinator.rootViewController = navController
        }
    }
}
