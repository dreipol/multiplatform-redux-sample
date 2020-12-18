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
            if let navControler = rootCoordinator.rootViewController as? UINavigationController,
               let lastScreen = navigationState.screens.last {
                if MainScreen.settings.isEqual(lastScreen) {
                    navControler.popViewController(animated: true)
                } else {
                    if let viewController = getControllerFor(screen: lastScreen) {
                        navControler.pushViewController(viewController, animated: true)
                    }
                }

            }
        }
    }

    private func getControllerFor(screen: Screen) -> UIViewController? {
        if MainScreen.zipSettings.isEqual(screen) {
            return ZipSettingsViewController()
        } else if MainScreen.notificationSettings.isEqual(screen) {
            return NotificationSettingsViewController()
        } else if MainScreen.calendarSettings.isEqual(screen) {
            return CalendarSettingsViewController()
        } else if MainScreen.languageSettings.isEqual(screen) {
            return LanguageSettingsViewController()
        }
        return nil
    }
}
