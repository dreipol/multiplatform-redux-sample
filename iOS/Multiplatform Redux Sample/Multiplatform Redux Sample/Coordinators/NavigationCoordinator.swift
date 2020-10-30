//
//  NavigationCoordinator.swift
//
//  Created by Samuel Bichsel on 23.07.20.
//  Copyright © 2020 dreipol GmbH. All rights reserved.
//

import Foundation
import ReduxSampleShared
import UIKit.UIApplication


class NavigationCoordinator: Navigator, Coordinator {

    func getNavigationState() -> NavigationState {
        return store.appState.navigationState
    }

    var store: Store

    lazy var onboardingCoordinator: OnboardingCoordinator = {
        OnboardingCoordinator(root: self)
    }()

    var state: NavigationState {
        // swiftlint:disable:next force_cast
        let appState = store.state as! AppState
        return appState.navigationState
    }

    var window: UIWindow?
    var windowStrong: UIWindow {
        get {
            guard let window = window else {
                fatalError("Window is nil")
            }
            return window
        }
    }
    var rootViewController: UIViewController? {
        get {

            return windowStrong.rootViewController
        }

        set {
            windowStrong.rootViewController = newValue
            windowStrong.makeKey()
        }
    }

    init(store: Store) {
        self.store = store
    }

    func setup(window: UIWindow?) {
        self.window = window
        NavigatorKt.subscribeNavigationState(self)
        updateNavigationState(navigationState: state)
    }

    func updateNavigationState(navigationState: NavigationState) {
        print(navigationState)
        switch navigationState.screens.last {
        case is OnboardingScreen:
            onboardingCoordinator.updateNavigationState(navigationState: navigationState)
//        case MainScreen.:
//            rootViewController = LoginViewController()
//        case is MainScreen:
//            rootViewController = MainViewController()
        default:
            print("Not implemented")
//            fatalError("Implement")
        }
    }
}
