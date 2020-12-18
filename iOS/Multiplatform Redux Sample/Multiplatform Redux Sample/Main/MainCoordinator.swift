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
        } else {
            if let navControler = rootCoordinator.rootViewController as? UINavigationController {
                let lastScreen = navigationState.screens.last
                //TODO handle correct logic for all cases
                if MainScreen.zipSettings.isEqual(lastScreen) {
                    navControler.pushViewController(ZipSettingsViewController(), animated: true)
                }
                if MainScreen.settings.isEqual(lastScreen) {
                    navControler.popViewController(animated: true)
                }
            }
        }
    }
}
