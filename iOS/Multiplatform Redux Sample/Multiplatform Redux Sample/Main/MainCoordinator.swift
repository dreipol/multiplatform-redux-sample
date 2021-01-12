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
            //Initialize after app start
            let navController = UINavigationController(rootViewController: MainViewController())
            navController.isNavigationBarHidden = true
            rootCoordinator.rootViewController = navController
        } else {
            if let navControler = rootCoordinator.rootViewController as? UINavigationController,
               let lastScreen = navigationState.screens.last {
                handleSettingsNavigation(lastScreen, navControler)
            }
        }
    }

    private func getControllerFor(screen: Screen) -> UIViewController? {
        guard let mainScreen = screen as? MainScreen else {
            return nil
        }

        let controller: UIViewController?
        switch mainScreen {
        case MainScreen.zipSettings:
            controller = ZipSettingsViewController()
        case MainScreen.notificationSettings:
            controller = NotificationSettingsViewController()
        case MainScreen.calendarSettings:
            controller = CalendarSettingsViewController()
        case MainScreen.languageSettings:
            controller = LanguageSettingsViewController()
        default:
            // Shouldn't happen, but a KotlinEnum is not a swift enum
            controller = nil
        }
        return controller
    }

    private func handleSettingsNavigation(_ lastScreen: Screen, _ navControler: UINavigationController) {
        if MainScreen.settings.isEqual(lastScreen) {
            navControler.popViewController(animated: true)
        } else if let viewController = getControllerFor(screen: lastScreen) {
            navControler.pushViewController(viewController, animated: true)
        }
    }
}
