//
//  MainCoordinator.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import Foundation
import rezhycleShared
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
               let lastScreen = navigationState.screens.last as? MainScreen {
                if lastScreen.isSettingSubNavigation() {
                    handleSettingsNavigation(lastScreen, navControler)
                }
            }
        }
    }

    private func getControllerFor(screen: MainScreen) -> UIViewController? {
        let controller: UIViewController?
        switch screen {
        case MainScreen.zipSettings:
            controller = ZipSettingsViewController()
        case MainScreen.notificationSettings:
            controller = NotificationSettingsViewController()
        case MainScreen.calendarSettings:
            controller = CalendarSettingsViewController()
        case MainScreen.languageSettings:
            controller = nil
        default:
            // Shouldn't happen, but a KotlinEnum is not a swift enum
            controller = nil
        }
        return controller
    }

    private func handleSettingsNavigation(_ lastScreen: MainScreen, _ navController: UINavigationController) {
        if lastScreen == MainScreen.settings {
            navController.popViewController(animated: true)
        } else {
            if let viewController = getControllerFor(screen: lastScreen) {
                navController.pushViewController(viewController, animated: true)
            } else {
                showLanguageAlert(navController)
            }
        }
    }

    private func showLanguageAlert(_ navController: UINavigationController) {
        let alertController = UIAlertController(title: "settings_language".localized,
                                                message: "settings_language_alert_text_ios".localized,
                                                preferredStyle: .alert)
        let cancelAction = UIAlertAction(title: "cancel_button".localized, style: .cancel, handler: { _ in
            _ = dispatch(NavigationAction.back)
        })
        let confirmAction = UIAlertAction(title: "button_settings".localized, style: .default) { _ in
            if let settingsUrl = URL(string: UIApplication.openSettingsURLString) {
                UIApplication.shared.open(settingsUrl)
            }
        }
        alertController.addAction(cancelAction)
        alertController.addAction(confirmAction)
        navController.present(alertController, animated: true, completion: nil)
    }
}

extension MainScreen {
    private static let settingScreens: Set = [MainScreen.settings, MainScreen.zipSettings, MainScreen.notificationSettings,
                                              MainScreen.calendarSettings, MainScreen.languageSettings]

    func isSettingSubNavigation() -> Bool {
        return Self.settingScreens.contains(self)
    }
}
