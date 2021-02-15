//
//  SceneDelegate.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 22.10.20.
//

import UIKit

class SceneDelegate: UIResponder, UIWindowSceneDelegate {

    var window: UIWindow?

    func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
        // Use this method to optionally configure and attach the UIWindow `window` to the provided UIWindowScene `scene`.
        // If using a storyboard, the `window` property will automatically be initialized and attached to the scene.
        // This delegate does not imply the connecting scene or session are new (see `application:configurationForConnectingSceneSession` instead).
        guard let windowScene = scene as? UIWindowScene else {
            return
        }

        guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else {
            fatalError("Unknown appDelegate")
        }
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.makeKeyAndVisible()
        window?.windowScene = windowScene

        // Currently several scenes per application are not supported
        appDelegate.coordinator.setup(window: window)
    }
}
