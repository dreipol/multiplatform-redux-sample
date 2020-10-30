//
//  AppDelegate.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 22.10.20.
//

import UIKit
import ReduxSampleShared


var dispatch: (Any) -> Any {
    guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
        fatalError("AppDelegate is wrong type")
    }
    return appDelegate.store.dispatch
}

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var store: Store!
    var coordinator: NavigationCoordinator!

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let sharedConfiguration = AppConfiguration(reduxSampleApp: ReduxSampleApp(), driverFactory: DriverFactory())
        store = sharedConfiguration.reduxSampleApp.store
        AppConfigurationKt.doInitApp(appConfig: sharedConfiguration)
        coordinator = NavigationCoordinator(store: store)
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }


}

